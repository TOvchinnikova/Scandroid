package com.t_ovchinnikova.android.scandroid_2.data.repository.impl

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.core_domain.repository.SettingsRepository
import com.t_ovchinnikova.android.scandroid_2.data.datasource.SettingsDataSource
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