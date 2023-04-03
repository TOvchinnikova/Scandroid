package com.t_ovchinnikova.android.scandroid_2.data.repository.impl

import com.t_ovchinnikova.android.scandroid_2.data.datasource.SettingsDataSource
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.core_domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val settingsDataSource: SettingsDataSource
) : SettingsRepository {
    override fun getSettings(): Flow<com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData> {
        return settingsDataSource.getSettings()
    }

    override suspend fun saveSettings(settings: com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData) {
        settingsDataSource.saveSettings(settings)
    }
}