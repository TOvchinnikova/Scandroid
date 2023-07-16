package com.t_ovchinnikova.android.scandroid_2.settings_impl.interactors

import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.settings_impl.repository.SettingsRepository
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class GetSettingsInteractor(
    private val settingsRepository: SettingsRepository
) : GetSettingsUseCase {

    override fun invokeAsync(): Flow<SettingsData> {
        return settingsRepository.getSettingsAsync()
    }

    override suspend fun invoke(): SettingsData? {
        return settingsRepository.getSettingsAsync().firstOrNull()
    }
}