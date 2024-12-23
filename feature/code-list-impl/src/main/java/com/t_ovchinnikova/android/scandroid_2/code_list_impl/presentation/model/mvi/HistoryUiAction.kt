package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi

import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.CodeUiModel
import com.t_ovchinnikova.android.scandroid_2.core_mvi.UiAction

sealed interface HistoryUiAction : UiAction {

    data object DeleteAllCodes : HistoryUiAction

    data class DeleteCode(val id: String) : HistoryUiAction

    data class ToggleFavourite(val code: CodeUiModel) : HistoryUiAction

    data class UpdateSearchCondition(val condition: String) : HistoryUiAction

    data class LongClickItem(val codeId: String) : HistoryUiAction

    data object CancelChecking : HistoryUiAction

    data class CheckCode(val codeId: String) : HistoryUiAction

    data object ShowDeleteDialog : HistoryUiAction

    data object HideDeleteDialog : HistoryUiAction
}