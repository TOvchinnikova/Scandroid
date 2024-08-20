package com.t_ovchinnikova.android.scandroid_2.code_details_impl.presentation.model.mvi

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_mvi.UiState

data class CodeDetailsUiState(
    val toolbarTitle: String = "",
    val code: Code? = null,
    val isSendingNoteWithCode: Boolean = false,
    val isLoading: Boolean = true,
    val isVisibleDeleteDialog: Boolean = false,
    val isVisibleCommentDialog: Boolean = false,
) : UiState

