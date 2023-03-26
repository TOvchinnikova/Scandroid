package com.t_ovchinnikova.android.scandroid_2.core_domain.entity

import java.util.*

data class Code(
    val id: UUID,
    val text: String,
    val format: Int,
    val type: Int,
    val date: Date = Date(),
    val note: String = "",
    val isFavorite: Boolean = false
)


