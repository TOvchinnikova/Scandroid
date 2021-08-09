package com.t_ovchinnikova.android.scandroid_2.database

import androidx.room.TypeConverter
import com.t_ovchinnikova.android.scandroid_2.model.BarcodeFormat
import com.t_ovchinnikova.android.scandroid_2.model.BarcodeType
import java.util.*

class BarcodeDatabaseTypeConverter {

    @TypeConverter
    fun fromBarcodeFormat(barcodeFormat: BarcodeFormat): String {
        return barcodeFormat.name
    }

    @TypeConverter
    fun toBarcodeFormat(value: String): BarcodeFormat {
        return BarcodeFormat.valueOf(value)
    }

    @TypeConverter
    fun fromBarcodeType(barcodeType: BarcodeType): String {
        return barcodeType.name
    }

    @TypeConverter
    fun toBarcodeType(value: String): BarcodeType {
        return BarcodeType.valueOf(value)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }

}