package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import com.t_ovchinnikova.android.scandroid_2.SettingsData
import kotlinx.coroutines.flow.Flow

interface GetSettingsUseCase {

    operator fun invoke(): Flow<SettingsData>
}