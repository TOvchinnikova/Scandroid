package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_mvi.UiState

data class HistoryUiState(
    val isLoading: Boolean,
    val codes: List<Code> = emptyList()
) : UiState
