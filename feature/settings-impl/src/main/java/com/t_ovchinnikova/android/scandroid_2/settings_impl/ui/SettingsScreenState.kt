package com.t_ovchinnikova.android.scandroid_2.settings_impl.ui

import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData

sealed class SettingsScreenState {
    object LoadingSettings : SettingsScreenState()
    data class SettingsScreen(val settings: SettingsData) : SettingsScreenState()
    object Failure : SettingsScreenState()
    object Initial : SettingsScreenState()
}
