package com.t_ovchinnikova.android.scandroid_2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "codes")
class CodeDbModel (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
    val format: Int,
    val type: Int,
    val date: Date = Date(),
    var note: String = "",
    var isFavorite: Boolean = false
)