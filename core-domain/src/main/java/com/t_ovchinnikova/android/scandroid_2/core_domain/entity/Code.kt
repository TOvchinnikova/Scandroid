package com.t_ovchinnikova.android.scandroid_2.core_domain.entity

import java.util.*

data class Code(
    val id: UUID,
    val text: String,
    val format: CodeFormat,
    val type: CodeType,
    val date: Date = Date(),
    val note: String = "",
    val isFavorite: Boolean = false
)


