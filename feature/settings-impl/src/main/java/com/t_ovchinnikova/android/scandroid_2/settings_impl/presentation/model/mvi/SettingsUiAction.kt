package com.t_ovchinnikova.android.scandroid_2.settings_impl.presentation.model.mvi

import com.t_ovchinnikova.android.scandroid_2.core_mvi.UiAction

sealed interface SettingsUiAction : UiAction {

    object ToggleVibrateOnScan : SettingsUiAction

    object ToggleEnableFlashOnScan : SettingsUiAction

    object ToggleSendingNote : SettingsUiAction
}