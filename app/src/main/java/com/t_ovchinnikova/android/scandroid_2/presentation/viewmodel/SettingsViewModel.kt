package com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.SettingsData
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.SaveSettingsUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val saveSettingsUseCase: SaveSettingsUseCase,
    getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val settingsFlow = getSettingsUseCase()
        .flowOn(IO)
        .filterNotNull()
        .onEach {
            Log.d("MyLog", "SettingsViewModel it: $it")
            loadingStateFlow.value = SettingsLoadingState.Hide
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    private val loadingStateFlow = MutableStateFlow<SettingsLoadingState>(
        SettingsLoadingState.Show
    )

    internal fun saveSettings(settings: SettingsData) {
        viewModelScope.launch {
            saveSettingsUseCase(settings)
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