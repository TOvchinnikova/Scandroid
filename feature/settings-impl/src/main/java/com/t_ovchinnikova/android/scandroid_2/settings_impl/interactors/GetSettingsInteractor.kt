package com.t_ovchinnikova.android.scandroid_2.settings_impl.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.settings_api.repository.SettingsRepository
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetSettingsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GetSettingsInteractor(
    private val settingsRepository: SettingsRepository
) : GetSettingsUseCase {

    override fun invokeAsync(): Flow<SettingsData> {
        return settingsRepository.getSettingsAsync()
    }

    override suspend fun invoke(): SettingsData {
        return settingsRepository.getSettingsAsync().first()
    }
}