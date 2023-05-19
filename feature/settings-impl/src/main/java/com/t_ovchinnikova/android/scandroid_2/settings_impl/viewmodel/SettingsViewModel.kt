package com.t_ovchinnikova.android.scandroid_2.settings_impl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.SaveSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_impl.ui.SettingsScreenState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val saveSettingsUseCase: SaveSettingsUseCase,
    getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow<SettingsScreenState>(
        SettingsScreenState.Initial)
    val screenState: StateFlow<SettingsScreenState> = _screenState

    private val settingsFlow = getSettingsUseCase.invokeAsync()
        .flowOn(IO)
        .filterNotNull()
        .onEach {
            _screenState.value = SettingsScreenState.SettingsScreen(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(),
            initialValue = null
        )

    init {
        viewModelScope.launch {
            _screenState.value = SettingsScreenState.LoadingSettings
            settingsFlow.collect()
        }
    }

    internal fun saveSettings(settings: SettingsData) {
        viewModelScope.launch {
            if (settings != settingsFlow.value) {
                saveSettingsUseCase(settings)
            }
        }
    }
}