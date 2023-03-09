package com.t_ovchinnikova.android.scandroid_2.domain

import android.os.Bundle
import androidx.navigation.NavType
import java.util.*

data class Code(
    val id: UUID,
    val text: String,
    val format: Int,
    val type: Int,
    val date: Date = Date(),
    val note: String = "",
    val isFavorite: Boolean = false
) {
    companion object {

        val NavigationType: NavType<UUID> = object : NavType<UUID>(false) {

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
    }
}


