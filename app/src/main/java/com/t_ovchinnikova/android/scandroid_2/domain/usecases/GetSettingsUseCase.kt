package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import com.t_ovchinnikova.android.scandroid_2.SettingsData
import kotlinx.coroutines.flow.Flow

interface GetSettingsUseCase {

    fun invokeAsync(): Flow<SettingsData>

    suspend fun invoke(): SettingsData
}