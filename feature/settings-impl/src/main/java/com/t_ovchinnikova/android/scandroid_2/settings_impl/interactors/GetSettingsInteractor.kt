package com.t_ovchinnikova.android.scandroid_2.settings_impl.interactors

import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_impl.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class GetSettingsInteractor(
    private val settingsRepository: SettingsRepository,
    private val dispatcher: CoroutineDispatcher
) : GetSettingsUseCase {

    override fun invokeAsync(): Flow<SettingsData> {
        return settingsRepository.getSettingsAsync()
    }

    override suspend fun invoke(): SettingsData? {
        return withContext(dispatcher) {
            settingsRepository.getSettingsAsync().firstOrNull()
        }
    }
}