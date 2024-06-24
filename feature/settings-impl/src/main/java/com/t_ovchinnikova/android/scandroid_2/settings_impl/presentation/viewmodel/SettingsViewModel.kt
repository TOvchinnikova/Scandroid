package com.t_ovchinnikova.android.scandroid_2.settings_impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.core_mvi.BaseViewModel
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.SaveSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_impl.presentation.model.mvi.SettingsUiAction
import com.t_ovchinnikova.android.scandroid_2.settings_impl.presentation.model.mvi.SettingsUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val dispatcher: CoroutineDispatcher
) : BaseViewModel<SettingsUiState, SettingsUiAction>() {

    init {
        observeSettings()
    }

    override fun getInitialState(): SettingsUiState = SettingsUiState()

    override fun onAction(action: SettingsUiAction) {
        when (action) {
            SettingsUiAction.ToggleVibrateOnScan -> onToggleVibrate()
            SettingsUiAction.ToggleEnableFlashOnScan -> onToggleEnabledFlash()
            SettingsUiAction.ToggleSendingNote -> onToggleSendingNote()
        }
    }

    private fun observeSettings() {
        getSettingsUseCase.invokeAsync()
            .flowOn(dispatcher)
            .filterNotNull()
            .onEach {
                updateState { copy(isLoading = false, settings = it) }
            }
            .launchIn(viewModelScope)
    }

    private fun onToggleVibrate() {
        uiState.value.settings?.let {
            viewModelScope.launch {
                saveSettingsUseCase(it.copy(isVibrationOnScan = !it.isVibrationOnScan))
            }
        }
    }

    private fun onToggleEnabledFlash() {
        uiState.value.settings?.let {
            viewModelScope.launch {
                saveSettingsUseCase(it.copy(isFlashlightWhenAppStarts = !it.isFlashlightWhenAppStarts))
            }
        }
    }

    private fun onToggleSendingNote() {
        uiState.value.settings?.let {
            viewModelScope.launch {
                saveSettingsUseCase(it.copy(isSendingNoteWithCode = !it.isSendingNoteWithCode))
            }
        }
    }
}