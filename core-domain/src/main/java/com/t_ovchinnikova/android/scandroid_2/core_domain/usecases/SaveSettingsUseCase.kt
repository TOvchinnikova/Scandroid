package com.t_ovchinnikova.android.scandroid_2.core_domain.usecases

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData

interface SaveSettingsUseCase {

    suspend operator fun invoke(settingsData: SettingsData)
}