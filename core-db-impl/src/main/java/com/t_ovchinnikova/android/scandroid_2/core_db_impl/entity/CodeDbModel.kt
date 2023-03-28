package com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.converters.CodeFormatConverter
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.converters.CodeTypeConverter
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeFormat
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeType
import java.util.*

@Entity(tableName = "codes")
class CodeDbModel (

    /**
     * Идентификатор кода
     */
    @PrimaryKey
    val id: UUID,

    /**
     * Код
     */
    val text: String,

    /**
     * Формат кода
     */
    @field:TypeConverters(CodeFormatConverter::class)
    val format: CodeFormat,

    /**
     * Тип кода
     */
    @field:TypeConverters(CodeTypeConverter::class)
    val type: CodeType,

    /**
     * Дата сканирования
     */
    val date: Date = Date(),

    /**
     * Комментарий
     */
    val note: String = "",

    /**
     * Признак "избранный"
     */
    val isFavorite: Boolean = false
)