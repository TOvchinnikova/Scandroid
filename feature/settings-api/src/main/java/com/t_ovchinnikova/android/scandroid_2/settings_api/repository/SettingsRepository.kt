package com.t_ovchinnikova.android.scandroid_2.settings_api.repository

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettingsAsync(): Flow<SettingsData>

    suspend fun getSettings(): SettingsData?

    suspend fun saveSettings(settings: SettingsData)
}