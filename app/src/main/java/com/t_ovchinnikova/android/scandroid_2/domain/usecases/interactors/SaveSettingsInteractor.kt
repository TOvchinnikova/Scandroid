package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.core_domain.repository.SettingsRepository
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.SaveSettingsUseCase

class SaveSettingsInteractor(
    private val settingsRepository: com.t_ovchinnikova.android.scandroid_2.core_domain.repository.SettingsRepository
) : com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.SaveSettingsUseCase {

    override suspend fun invoke(settingsData: com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData) {
        settingsRepository.saveSettings(settingsData)
    }
}