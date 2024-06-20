package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_mvi.UiAction
import java.util.UUID

sealed interface HistoryUiAction : UiAction {

    object DeleteAllCodes : HistoryUiAction

    data class DeleteCode(val id: UUID) : HistoryUiAction

    data class ToggleFavourite(val code: Code) : HistoryUiAction

    data class UpdateSearchCondition(val condition: String) : HistoryUiAction
}