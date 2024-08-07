package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi

import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.CodeUiModel
import com.t_ovchinnikova.android.scandroid_2.core_mvi.UiState

data class HistoryUiState(
    val isLoading: Boolean,
    val codes: List<CodeUiModel> = emptyList(),
    val isVisibleCheckBox: Boolean = false,
    val isVisibleDeleteDialog: Boolean = false
) : UiState
