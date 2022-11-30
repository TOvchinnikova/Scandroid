package com.t_ovchinnikova.android.scandroid_2.data.repository

import com.t_ovchinnikova.android.scandroid_2.data.entity.SettingsData
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettings(): Flow<SettingsData>

    suspend fun saveSettings(settings: SettingsData)
}