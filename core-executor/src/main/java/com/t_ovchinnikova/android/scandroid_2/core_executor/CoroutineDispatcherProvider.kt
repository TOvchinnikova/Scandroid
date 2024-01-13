package com.t_ovchinnikova.android.scandroid_2.core_executor

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}