package com.t_ovchinnikova.android.scandroid_2.di

import com.t_ovchinnikova.android.scandroid_2.data.CodeDao
import com.t_ovchinnikova.android.scandroid_2.data.CodeDatabase
import com.t_ovchinnikova.android.scandroid_2.data.CodeMapper
import com.t_ovchinnikova.android.scandroid_2.data.CodeRepositoryImpl
import com.t_ovchinnikova.android.scandroid_2.domain.CodeRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    single<CodeDatabase> {
        CodeDatabase.newInstance(application = androidApplication())
    }

    single<CodeRepository> {
        CodeRepositoryImpl.initialize(mapper = get(), codeDao = get())
    }

    single<CodeDao> {
        get<CodeDatabase>().codeDao()
    }

    single<CodeMapper> {
        CodeMapper()
    }

}