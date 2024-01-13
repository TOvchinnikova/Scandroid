package com.t_ovchinnikova.android.scandroid_2.di

import com.t_ovchinnikova.android.scandroid_2.core_executor.CoroutineDispatcherProvider
import com.t_ovchinnikova.android.scandroid_2.executor.CoroutineDispatcherProviderImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val applicationModule = module {

    single<CoroutineDispatcherProvider> {
        CoroutineDispatcherProviderImpl(
            io = Dispatchers.IO,
            main = Dispatchers.Main,
            default = Dispatchers.Default
        )
    }
}
