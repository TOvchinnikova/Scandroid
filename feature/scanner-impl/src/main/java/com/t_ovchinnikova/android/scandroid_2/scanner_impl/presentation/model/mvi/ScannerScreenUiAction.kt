package com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.model.mvi

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_mvi.UiAction

sealed interface ScannerScreenUiAction : UiAction {

    data object SwitchFlash : ScannerScreenUiAction

    data class CodeScanned(val code: Code) : ScannerScreenUiAction
}