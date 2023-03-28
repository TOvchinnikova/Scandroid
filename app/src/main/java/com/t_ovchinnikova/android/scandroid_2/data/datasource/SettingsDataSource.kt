package com.t_ovchinnikova.android.scandroid_2.data.datasource

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData
import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {

    fun getSettings(): Flow<com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData>

    suspend fun saveSettings(settings: com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData)
}