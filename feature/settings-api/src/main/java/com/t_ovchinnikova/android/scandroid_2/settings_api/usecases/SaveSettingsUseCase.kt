package com.t_ovchinnikova.android.scandroid_2.settings_api.usecases

import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData

interface SaveSettingsUseCase {

    suspend operator fun invoke(settingsData: SettingsData)
}