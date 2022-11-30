package com.t_ovchinnikova.android.scandroid_2.data.datasource

import com.t_ovchinnikova.android.scandroid_2.data.entity.SettingsData
import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {

    fun getSettings(): Flow<SettingsData>

    suspend fun saveSettings(settings: SettingsData)
}