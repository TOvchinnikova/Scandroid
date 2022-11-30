package com.t_ovchinnikova.android.scandroid_2.domain

import java.util.*

data class Code(
    val id: Long = UNDEFINED_ID,
    val text: String,
    val format: Int,
    val type: Int,
    val date: Date = Date(),
    val note: String = "",
    val isFavorite: Boolean = false
) {

    companion object {
        const val UNDEFINED_ID = 0L
    }

}


