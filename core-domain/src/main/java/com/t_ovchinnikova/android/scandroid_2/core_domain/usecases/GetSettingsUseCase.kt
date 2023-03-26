package com.t_ovchinnikova.android.scandroid_2.core_domain.usecases

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData
import kotlinx.coroutines.flow.Flow

interface GetSettingsUseCase {

    fun invokeAsync(): Flow<SettingsData>

    suspend fun invoke(): SettingsData
}