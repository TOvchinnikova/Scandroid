package com.t_ovchinnikova.android.scandroid_2.core_db_impl.converters

import androidx.room.TypeConverter
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeType

object CodeTypeConverter {

    @JvmStatic
    @TypeConverter
    fun toCodeType(value: Int?): CodeType? {
        value?.let {
            for (v in CodeType.values()) {
                if (v.tag == value) {
                    return v
                }
            }
            throw IllegalArgumentException("Unknown CodeType value: $value")
        } ?: return null
    }

    @JvmStatic
    @TypeConverter
    fun fromCodeType(codeType: CodeType?): Int? {
        codeType?.let {
            for (v in CodeType.values()) {
                if (v.tag == codeType.tag) {
                    return v.tag
                }
            }
            throw java.lang.IllegalArgumentException("Unknown CodeType: $codeType")
        } ?: return null
    }
}