package com.t_ovchinnikova.android.scandroid_2.di

import com.t_ovchinnikova.android.scandroid_2.core_db_impl.di.databaseModule

val appModules = mutableListOf(
    appModule,
    domainModule,
    dataModule,
    databaseModule
)
