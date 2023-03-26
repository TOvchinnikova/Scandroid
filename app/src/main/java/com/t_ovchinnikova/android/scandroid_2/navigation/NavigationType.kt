package com.t_ovchinnikova.android.scandroid_2

import android.os.Bundle
import androidx.navigation.NavType
import java.util.*

val CodeNavigationType: NavType<UUID> = object : NavType<UUID>(false) {

    override fun get(bundle: Bundle, key: String): UUID? {
        return bundle.getSerializable(key) as UUID
    }

    override fun parseValue(value: String): UUID {
        return UUID.fromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: UUID) {
        bundle.putSerializable(key, value)
    }
}