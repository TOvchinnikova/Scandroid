package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.SettingsData
import com.t_ovchinnikova.android.scandroid_2.data.datasource.SettingsDataSource
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetSettingsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GetSettingsInteractor(
    private val dataSource: SettingsDataSource
) : GetSettingsUseCase {

    override fun invokeAsync(): Flow<SettingsData> {
        return dataSource.getSettings()
    }

    override suspend fun invoke(): SettingsData {
        return dataSource.getSettings().first()
    }
}