package com.t_ovchinnikova.android.scandroid_2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.mlkit.vision.barcode.Barcode
import java.io.Serializable
import java.util.*


@Entity(tableName = "codes")
data class Code(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val format: Int,
    val type: Int,
    val date: Date = Date()
) : Serializable