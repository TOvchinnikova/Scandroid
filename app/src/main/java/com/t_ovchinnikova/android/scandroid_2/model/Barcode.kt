package com.t_ovchinnikova.android.scandroid_2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*


@Entity(tableName = "codes")
data class Barcode(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val text: String,
    val format: BarcodeFormat,
    val type: BarcodeType,
    val date: Date = Date()
) : Serializable