package com.t_ovchinnikova.android.scandroid_2.settings_impl.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.SaveSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_api.repository.SettingsRepository

class SaveSettingsInteractor(
    private val settingsRepository: SettingsRepository
) : SaveSettingsUseCase {

    override suspend fun invoke(settingsData: SettingsData) {
        settingsRepository.saveSettings(settingsData)
    }
}