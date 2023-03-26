package com.t_ovchinnikova.android.scandroid_2.core_db_impl.di

import com.t_ovchinnikova.android.scandroid_2.core_db_impl.CodeDao
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.CodeDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single<CodeDatabase> {
        CodeDatabase.newInstance(application = androidApplication())
    }

    single<CodeDao> {
        get<CodeDatabase>().codeDao()
    }
}