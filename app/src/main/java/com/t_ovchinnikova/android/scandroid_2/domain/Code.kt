package com.t_ovchinnikova.android.scandroid_2.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Code(
    var id: Long = UNDEFINED_ID,
    val text: String,
    val format: Int,
    val type: Int,
    val date: Date = Date(),
    var note: String = "",
    var isFavorite: Boolean = false
) : Parcelable {

    companion object {
        const val UNDEFINED_ID = 0L
    }

}


