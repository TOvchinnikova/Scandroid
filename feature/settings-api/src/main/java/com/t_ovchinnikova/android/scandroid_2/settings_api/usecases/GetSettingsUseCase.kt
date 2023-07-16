package com.t_ovchinnikova.android.scandroid_2.settings_api.usecases

import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData
import kotlinx.coroutines.flow.Flow

interface GetSettingsUseCase {

    fun invokeAsync(): Flow<SettingsData>

    suspend fun invoke(): SettingsData?
}