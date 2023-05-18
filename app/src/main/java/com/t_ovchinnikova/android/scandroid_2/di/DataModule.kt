package com.t_ovchinnikova.android.scandroid_2.di

import com.t_ovchinnikova.android.scandroid_2.settings_impl.datasource.SettingsDataSourceImpl
import com.t_ovchinnikova.android.scandroid_2.settings_impl.datasource.SettingsDataSourceImpl.Companion.getSettingsDataStore
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.CodeDao
import com.t_ovchinnikova.android.scandroid_2.data.CodeMapper
import com.t_ovchinnikova.android.scandroid_2.data.repository.impl.CodeRepositoryImpl
import com.t_ovchinnikova.android.scandroid_2.data.datasource.CodeDataSource
import com.t_ovchinnikova.android.scandroid_2.settings_impl.datasource.SettingsDataSource
import com.t_ovchinnikova.android.scandroid_2.data.datasource.impl.CodeDataSourceImpl
import com.t_ovchinnikova.android.scandroid_2.core_domain.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.settings_impl.repository.SettingsRepository
import com.t_ovchinnikova.android.scandroid_2.data.datasource.InMemoryCodeDataStore
import com.t_ovchinnikova.android.scandroid_2.data.datasource.impl.InMemoryCodeDataStoreImpl
import com.t_ovchinnikova.android.scandroid_2.settings_impl.repository.SettingsRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single<CodeRepository> {
        CodeRepositoryImpl(
            codeMapper = get() as CodeMapper,
            codeDataSource = get() as CodeDataSource,
            inMemoryCodeDataStore = get() as InMemoryCodeDataStore,
            settingsRepository = get() as SettingsRepository
        )
    }

    single<CodeDataSource> {
        CodeDataSourceImpl(
            codeDao = get() as CodeDao
        )
    }

    single<InMemoryCodeDataStore> {
        InMemoryCodeDataStoreImpl()
    }

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

    single<CodeMapper> {
        CodeMapper()
    }
}