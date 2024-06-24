package com.t_ovchinnikova.android.scandroid_2.settings_impl.domain.repository

import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettingsAsync(): Flow<SettingsData>

    suspend fun getSettings(): SettingsData?

    suspend fun saveSettings(settings: SettingsData)
}