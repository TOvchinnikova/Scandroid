package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.data.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.data.repository.SettingsRepository
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetSettingsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GetSettingsInteractor(
    private val settingsRepository: SettingsRepository
) : GetSettingsUseCase {

    override fun invokeAsync(): Flow<SettingsData> {
        return settingsRepository.getSettings()
    }

    override suspend fun invoke(): SettingsData {
        return settingsRepository.getSettings().first()
    }
}