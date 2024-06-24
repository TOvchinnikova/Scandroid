package com.t_ovchinnikova.android.scandroid_2.settings_impl.domain.interactor

import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.SaveSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_impl.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SaveSettingsInteractor(
    private val settingsRepository: SettingsRepository,
    private val dispatcher: CoroutineDispatcher
) : SaveSettingsUseCase {

    override suspend fun invoke(settingsData: SettingsData) {
        withContext(dispatcher) {
            settingsRepository.saveSettings(settingsData)
        }
    }
}