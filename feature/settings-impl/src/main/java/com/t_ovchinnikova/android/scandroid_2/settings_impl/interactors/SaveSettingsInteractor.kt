package com.t_ovchinnikova.android.scandroid_2.settings_impl.interactors

import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.SaveSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_impl.repository.SettingsRepository

class SaveSettingsInteractor(
    private val settingsRepository: SettingsRepository
) : SaveSettingsUseCase {

    override suspend fun invoke(settingsData: SettingsData) {
        settingsRepository.saveSettings(settingsData)
    }
}