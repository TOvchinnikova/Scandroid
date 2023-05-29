package com.t_ovchinnikova.android.scandroid_2.core_utils

import android.os.Build
import android.os.Bundle
import java.io.Serializable

fun <T : Serializable?> Bundle.getSerializableArgument(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializable(key, clazz)
    } else {
        this.getSerializable(key) as T?
    }
}