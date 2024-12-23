package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeFormat
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeType

data class CodeItemUiModel(
    val code: CodeUiModel,
    val isChecked: Boolean = false,
)

data class CodeUiModel(
    val id: String,
    val text: String,
    val format: CodeFormat,
    val type: CodeType,
    val date: String = "",
    val note: String = "",
    val isFavorite: Boolean = false
)