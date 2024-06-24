package com.t_ovchinnikova.android.scandroid_2.settings_impl.di

import com.t_ovchinnikova.android.scandroid_2.core_executor.CoroutineDispatcherProvider
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.SaveSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_impl.domain.repository.SettingsRepository
import com.t_ovchinnikova.android.scandroid_2.settings_impl.presentation.viewmodel.SettingsViewModel
import com.t_ovchinnikova.android.scandroid_2.settings_impl.data.datasource.SettingsDataSource
import com.t_ovchinnikova.android.scandroid_2.settings_impl.data.datasource.SettingsDataSourceImpl
import com.t_ovchinnikova.android.scandroid_2.settings_impl.data.datasource.SettingsDataSourceImpl.Companion.getSettingsDataStore
import com.t_ovchinnikova.android.scandroid_2.settings_impl.domain.interactor.GetSettingsInteractor
import com.t_ovchinnikova.android.scandroid_2.settings_impl.domain.interactor.SaveSettingsInteractor
import com.t_ovchinnikova.android.scandroid_2.settings_impl.data.repository.SettingsRepositoryImpl
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