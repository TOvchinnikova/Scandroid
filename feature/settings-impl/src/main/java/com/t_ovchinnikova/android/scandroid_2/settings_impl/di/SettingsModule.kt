package com.t_ovchinnikova.android.scandroid_2.settings_impl.di

import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.SaveSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_api.repository.SettingsRepository
import com.t_ovchinnikova.android.scandroid_2.settings_impl.SettingsViewModel
import com.t_ovchinnikova.android.scandroid_2.settings_impl.datasource.SettingsDataSource
import com.t_ovchinnikova.android.scandroid_2.settings_impl.datasource.SettingsDataSourceImpl
import com.t_ovchinnikova.android.scandroid_2.settings_impl.datasource.SettingsDataSourceImpl.Companion.getSettingsDataStore
import com.t_ovchinnikova.android.scandroid_2.settings_impl.interactors.GetSettingsInteractor
import com.t_ovchinnikova.android.scandroid_2.settings_impl.interactors.SaveSettingsInteractor
import com.t_ovchinnikova.android.scandroid_2.settings_impl.repository.SettingsRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {

    single<SettingsDataSource> {
        SettingsDataSourceImpl(
            dataStore = androidContext().getSettingsDataStore()
        )
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(
            settingsDataSource = get() as SettingsDataSource
        )
    }

    factory<SaveSettingsUseCase> {
        SaveSettingsInteractor(
            settingsRepository = get() as SettingsRepository
        )
    }

    factory<GetSettingsUseCase> {
        GetSettingsInteractor(
            settingsRepository = get() as SettingsRepository
        )
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(
            saveSettingsUseCase = get() as SaveSettingsUseCase,
            getSettingsUseCase = get() as GetSettingsUseCase
        )
    }
}