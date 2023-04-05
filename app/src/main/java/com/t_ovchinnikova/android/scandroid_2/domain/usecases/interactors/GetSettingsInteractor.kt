package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GetSettingsInteractor(
    private val settingsRepository: com.t_ovchinnikova.android.scandroid_2.core_domain.repository.SettingsRepository
) : com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetSettingsUseCase {

    override fun invokeAsync(): Flow<com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData> {
        return settingsRepository.getSettingsAsync()
    }

    override suspend fun invoke(): com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData {
        return settingsRepository.getSettingsAsync().first()
    }
}