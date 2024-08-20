package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code

data class CodeUiModel(
    val code: Code,
    val isChecked: Boolean = false,
)
