package com.t_ovchinnikova.android.scandroid_2.data.db

import androidx.room.TypeConverter
import java.util.*

class CodeDatabaseTypeConverter {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }

    @TypeConverter
    fun fromBoolean(boolean: Boolean): Int {
        return boolean.toInt()
    }

    @TypeConverter
    fun toBoolean(int: Int): Boolean {
        return int.toBoolean()
    }

    @TypeConverter
    fun toUuid(uuid: String?): UUID? {
        return uuid?.let { UUID.fromString(it) }
    }

//    @TypeConverter
//    fun toUuid(uuid: Long?): UUID? {
//        return uuid?.let { UUID.fromString(it.toString()) }
//    }

    @TypeConverter
    fun toString(uuid: UUID?): String? {
        return uuid?.toString()
    }
}

fun Boolean.toInt() = if (this) 1 else 0

fun Int.toBoolean() = this == 1