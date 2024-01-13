package com.t_ovchinnikova.android.scandroid_2.executor

import com.t_ovchinnikova.android.scandroid_2.core_executor.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher

data class CoroutineDispatcherProviderImpl(
    override val io: CoroutineDispatcher,
    override val main: CoroutineDispatcher,
    override val default: CoroutineDispatcher
) : CoroutineDispatcherProvider
