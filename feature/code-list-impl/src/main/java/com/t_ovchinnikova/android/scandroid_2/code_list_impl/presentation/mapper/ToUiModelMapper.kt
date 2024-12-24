package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.mapper

import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.CodeUiModel
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity
import com.t_ovchinnikova.android.scandroid_2.core_ui.DATE_PATTERN_STRING
import com.t_ovchinnikova.android.scandroid_2.core_utils.toStringByPattern
import java.text.SimpleDateFormat
import java.util.Locale

fun CodeEntity.toUiModel(): CodeUiModel = CodeUiModel(
    id = this.id.toString(),
    text = this.text,
    format = this.format,
    type = this.type,
    dateTime = this.date.toStringByPattern(
        SimpleDateFormat(DATE_PATTERN_STRING, Locale.getDefault())
    ),
    note = this.note,
    isFavorite = this.isFavorite
)