package com.t_ovchinnikova.android.scandroid_2.ui.settings

import com.t_ovchinnikova.android.scandroid_2.data.entity.SettingsData

sealed class SettingsScreenState {
    object LoadingSettings : SettingsScreenState()
    data class SettingsScreen(val settings: SettingsData) : SettingsScreenState()
    object Failure : SettingsScreenState()
    object Initial : SettingsScreenState()
}
