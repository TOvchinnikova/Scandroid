package com.t_ovchinnikova.android.scandroid_2.di

import com.t_ovchinnikova.android.scandroid_2.SettingsDataSourceImpl
import com.t_ovchinnikova.android.scandroid_2.SettingsDataSourceImpl.Companion.getSettingsDataStore
import com.t_ovchinnikova.android.scandroid_2.data.CodeDao
import com.t_ovchinnikova.android.scandroid_2.data.CodeDatabase
import com.t_ovchinnikova.android.scandroid_2.data.CodeMapper
import com.t_ovchinnikova.android.scandroid_2.data.repository.impl.CodeRepositoryImpl
import com.t_ovchinnikova.android.scandroid_2.data.datasource.CodeDataSource
import com.t_ovchinnikova.android.scandroid_2.data.datasource.SettingsDataSource
import com.t_ovchinnikova.android.scandroid_2.data.datasource.impl.CodeDataSourceImpl
import com.t_ovchinnikova.android.scandroid_2.data.repository.CodeRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single<CodeDatabase> {
        CodeDatabase.newInstance(application = androidApplication())
    }

    single<CodeRepository> {
        CodeRepositoryImpl(
            codeMapper = get() as CodeMapper,
            codeDataSource = get() as CodeDataSource
        )
    }

    single<CodeDataSource> {
        CodeDataSourceImpl(
            codeDao = get() as CodeDao
        )
    }

    single<SettingsDataSource> {
        SettingsDataSourceImpl(
            dataStore = androidContext().getSettingsDataStore()
        )
    }

    single<CodeDao> {
        get<CodeDatabase>().codeDao()
    }

    single<CodeMapper> {
        CodeMapper()
    }

}