package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.SettingsData
import com.t_ovchinnikova.android.scandroid_2.data.datasource.SettingsDataSource
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.SaveSettingsUseCase

class SaveSettingsInteractor(
    private val dataSource: SettingsDataSource
) : SaveSettingsUseCase {

    override suspend fun invoke(settingsData: SettingsData) {
        dataSource.saveSettings(settingsData)
    }
}