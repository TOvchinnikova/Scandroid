package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.data.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.data.repository.SettingsRepository
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.SaveSettingsUseCase

class SaveSettingsInteractor(
    private val settingsRepository: SettingsRepository
) : SaveSettingsUseCase {

    override suspend fun invoke(settingsData: SettingsData) {
        settingsRepository.saveSettings(settingsData)
    }
}