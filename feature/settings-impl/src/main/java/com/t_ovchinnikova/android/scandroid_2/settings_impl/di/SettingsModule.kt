package com.t_ovchinnikova.android.scandroid_2.settings_impl.di

import com.t_ovchinnikova.android.scandroid_2.core_executor.CoroutineDispatcherProvider
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.SaveSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_impl.repository.SettingsRepository
import com.t_ovchinnikova.android.scandroid_2.settings_impl.viewmodel.SettingsViewModel
import com.t_ovchinnikova.android.scandroid_2.settings_impl.datasource.SettingsDataSource
import com.t_ovchinnikova.android.scandroid_2.settings_impl.datasource.SettingsDataSourceImpl
import com.t_ovchinnikova.android.scandroid_2.settings_impl.datasource.SettingsDataSourceImpl.Companion.getSettingsDataStore
import com.t_ovchinnikova.android.scandroid_2.settings_impl.interactors.GetSettingsInteractor
import com.t_ovchinnikova.android.scandroid_2.settings_impl.interactors.SaveSettingsInteractor
import com.t_ovchinnikova.android.scandroid_2.settings_impl.repository.impl.SettingsRepositoryImpl
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
            settingsRepository = get() as SettingsRepository,
            dispatcher = (get() as CoroutineDispatcherProvider).io
        )
    }

    factory<GetSettingsUseCase> {
        GetSettingsInteractor(
            settingsRepository = get() as SettingsRepository,
            dispatcher = (get() as CoroutineDispatcherProvider).io
        )
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(
            saveSettingsUseCase = get() as SaveSettingsUseCase,
            getSettingsUseCase = get() as GetSettingsUseCase,
            dispatcher = (get() as CoroutineDispatcherProvider).io
        )
    }
}