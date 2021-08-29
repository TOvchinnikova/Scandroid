package com.t_ovchinnikova.android.scandroid_2.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.mlkit.vision.barcode.Barcode
import java.io.Serializable
import java.util.*


@Entity(tableName = "codes")
data class Code(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
    val format: Int,
    val type: Int,
    val date: Date = Date(),
    var note: String = "",
    var isFavorite: Boolean = false
) : Serializable

