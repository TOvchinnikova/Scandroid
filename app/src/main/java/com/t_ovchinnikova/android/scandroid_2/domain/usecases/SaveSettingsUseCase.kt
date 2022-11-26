package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import com.t_ovchinnikova.android.scandroid_2.SettingsData

interface SaveSettingsUseCase {

    suspend operator fun invoke(settingsData: SettingsData)
}