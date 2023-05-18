package com.t_ovchinnikova.android.scandroid_2.settings_impl.datasource

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData
import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {

    fun getSettings(): Flow<SettingsData>

    suspend fun saveSettings(settings: SettingsData)
}