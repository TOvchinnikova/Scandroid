package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi

import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.CodeItemUiModel
import com.t_ovchinnikova.android.scandroid_2.core_mvi.UiState

data class HistoryUiState(
    val isLoading: Boolean,
    val codes: List<CodeItemUiModel> = emptyList(),
    val isVisibleCheckBox: Boolean = false,
    val isVisibleDeleteDialog: Boolean = false,
    val searchCondition: String = "",
) : UiState
