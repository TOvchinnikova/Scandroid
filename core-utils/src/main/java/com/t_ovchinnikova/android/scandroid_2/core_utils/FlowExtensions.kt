package com.t_ovchinnikova.android.scandroid_2.core_utils

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.launchWhenStarted(lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launchWhenStarted lws@{ //todo deprecated
        this@launchWhenStarted.collect()
    }
}