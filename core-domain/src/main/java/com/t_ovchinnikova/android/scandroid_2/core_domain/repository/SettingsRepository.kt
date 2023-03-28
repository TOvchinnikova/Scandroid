package com.t_ovchinnikova.android.scandroid_2.core_domain.repository

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettings(): Flow<SettingsData>

    suspend fun saveSettings(settings: SettingsData)
}