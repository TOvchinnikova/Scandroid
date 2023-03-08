package com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "codes")
class CodeDbModel (
    @PrimaryKey
    val id: UUID,
    val text: String,
    val format: Int,
    val type: Int,
    val date: Date = Date(),
    val note: String = "",
    val isFavorite: Boolean = false
)