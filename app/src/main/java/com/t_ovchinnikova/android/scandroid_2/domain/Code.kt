package com.t_ovchinnikova.android.scandroid_2.domain

import java.io.Serializable
import java.util.*

data class Code(
    val id: Long = UNDEFINED_ID,
    val text: String,
    val format: Int,
    val type: Int,
    val date: Date = Date(),
    var note: String = "",
    var isFavorite: Boolean = false
) : Serializable {

    companion object {
        const val UNDEFINED_ID = 0L
    }

}


