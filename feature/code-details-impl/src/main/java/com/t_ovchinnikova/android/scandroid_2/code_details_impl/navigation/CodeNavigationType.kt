package com.t_ovchinnikova.android.scandroid_2.code_details_impl.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.t_ovchinnikova.android.scandroid_2.core_utils.getSerializableArgument
import java.util.*

val CodeNavigationType: NavType<UUID> = object : NavType<UUID>(false) {

    override fun get(bundle: Bundle, key: String): UUID {
        return bundle.getSerializableArgument(key, UUID::class.java)
            ?: throw RuntimeException("Args is null")
    }

    override fun parseValue(value: String): UUID {
        return UUID.fromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: UUID) {
        bundle.putSerializable(key, value)
    }
}