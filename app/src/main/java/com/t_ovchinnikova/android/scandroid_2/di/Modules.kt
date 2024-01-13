package com.t_ovchinnikova.android.scandroid_2.di

import com.t_ovchinnikova.android.scandroid_2.code_details_impl.di.codeDetailsModule
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.di.codeListModule
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.di.databaseModule
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.di.scannerModule
import com.t_ovchinnikova.android.scandroid_2.settings_impl.di.settingsModule

val appModules = mutableListOf(
    applicationModule,
    databaseModule,
    codeDetailsModule,
    scannerModule,
    settingsModule,
    codeListModule
)
