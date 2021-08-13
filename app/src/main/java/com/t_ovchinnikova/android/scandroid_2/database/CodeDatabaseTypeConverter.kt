package com.t_ovchinnikova.android.scandroid_2.database

import androidx.room.TypeConverter
import com.t_ovchinnikova.android.scandroid_2.model.CodeFormat
import com.t_ovchinnikova.android.scandroid_2.model.CodeType
import java.util.*

class CodeDatabaseTypeConverter {

   /* @TypeConverter
    fun fromBarcodeFormat(codeFormat: CodeFormat): String {
        return codeFormat.name
    }

    @TypeConverter
    fun toBarcodeFormat(value: String): CodeFormat {
        return CodeFormat.valueOf(value)
    }

    @TypeConverter
    fun fromBarcodeType(codeType: CodeType): String {
        return codeType.name
    }

    @TypeConverter
    fun toBarcodeType(value: String): CodeType {
        return CodeType.valueOf(value)
    }*/

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }

}