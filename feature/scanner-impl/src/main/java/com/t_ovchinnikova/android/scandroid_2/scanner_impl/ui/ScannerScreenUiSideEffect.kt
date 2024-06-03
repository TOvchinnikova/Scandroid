package com.t_ovchinnikova.android.scandroid_2.scanner_impl.ui

import java.util.UUID

sealed interface ScannerScreenUiSideEffect {

    object Vibrate : ScannerScreenUiSideEffect

    data class OpenCodeDetails(val codeId: UUID) : ScannerScreenUiSideEffect
}