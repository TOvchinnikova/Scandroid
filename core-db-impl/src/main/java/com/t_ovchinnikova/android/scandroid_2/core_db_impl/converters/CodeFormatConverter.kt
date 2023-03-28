package com.t_ovchinnikova.android.scandroid_2.core_db_impl.converters

import androidx.room.TypeConverter
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeFormat

object CodeFormatConverter {

    @JvmStatic
    @TypeConverter
    fun toCodeFormat(value: Int?): CodeFormat? {
        value?.let {
            for (v in CodeFormat.values()) {
                if (v.tag == value) {
                    return v
                }
            }
            throw IllegalArgumentException("Unknown CodeFormat value: $value")
        } ?: return null
    }

    @JvmStatic
    @TypeConverter
    fun fromCodeType(codeFormat: CodeFormat?): Int? {
        codeFormat?.let {
            for (v in CodeFormat.values()) {
                if (v.tag == codeFormat.tag) {
                    return v.tag
                }
            }
            throw java.lang.IllegalArgumentException("Unknown CodeFormat: $codeFormat")
        } ?: return null
    }
}