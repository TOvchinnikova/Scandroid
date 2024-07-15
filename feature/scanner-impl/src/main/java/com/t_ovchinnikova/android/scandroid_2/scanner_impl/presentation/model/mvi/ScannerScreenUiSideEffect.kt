package com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.model.mvi

import java.util.UUID

sealed interface ScannerScreenUiSideEffect {

    data class OpenCodeDetails(val codeId: UUID) : ScannerScreenUiSideEffect
}