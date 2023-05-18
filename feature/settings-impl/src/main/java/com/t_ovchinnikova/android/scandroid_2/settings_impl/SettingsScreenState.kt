package com.t_ovchinnikova.android.scandroid_2.settings_impl

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData

sealed class SettingsScreenState {
    object LoadingSettings : SettingsScreenState()
    data class SettingsScreen(val settings: com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData) : SettingsScreenState()
    object Failure : SettingsScreenState()
    object Initial : SettingsScreenState()
}
