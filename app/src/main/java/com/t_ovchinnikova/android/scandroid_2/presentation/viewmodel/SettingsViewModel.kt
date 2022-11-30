package com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.data.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.SaveSettingsUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val saveSettingsUseCase: SaveSettingsUseCase,
    getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val settingsFlow = getSettingsUseCase.invokeAsync()
        .flowOn(IO)
        .filterNotNull()
        .onEach {
            loadingStateFlow.value = SettingsLoadingState.Hide
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(),
            initialValue = null
        )

    private val loadingStateFlow = MutableStateFlow<SettingsLoadingState>(
        SettingsLoadingState.Show
    )

    internal fun saveSettings(settings: SettingsData) {
        viewModelScope.launch {
            if (settings != settingsFlow.value) {
                saveSettingsUseCase(settings)
            }
        }
    }

    fun getSettingsObservable(): StateFlow<SettingsData?> {
        return settingsFlow
    }

    fun getLoadingStateObservable(): StateFlow<SettingsLoadingState> {
        return loadingStateFlow
    }

    sealed class SettingsLoadingState {
        object Show : SettingsLoadingState()
        object Hide : SettingsLoadingState()
    }
}