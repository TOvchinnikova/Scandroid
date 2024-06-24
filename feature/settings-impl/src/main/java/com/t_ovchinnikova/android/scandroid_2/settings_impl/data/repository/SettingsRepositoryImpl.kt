package com.t_ovchinnikova.android.scandroid_2.settings_impl.data.repository

import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.settings_impl.data.datasource.SettingsDataSource
import com.t_ovchinnikova.android.scandroid_2.settings_impl.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class SettingsRepositoryImpl(
    private val settingsDataSource: SettingsDataSource
) : SettingsRepository {
    override fun getSettingsAsync(): Flow<SettingsData> {
        return settingsDataSource.getSettings()
    }

    override suspend fun getSettings(): SettingsData? {
        return settingsDataSource.getSettings().firstOrNull()
    }

    override suspend fun saveSettings(settings: SettingsData) {
        settingsDataSource.saveSettings(settings)
    }
}