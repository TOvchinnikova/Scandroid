package com.t_ovchinnikova.android.scandroid_2.ui.scanner

import com.t_ovchinnikova.android.scandroid_2.domain.Code

sealed class ScannerScreenState {
    class Working(val isFlashlightWorks: Boolean, val lastScannedCode: Code?) : ScannerScreenState()
    object Paused : ScannerScreenState()
    object SavingCode : ScannerScreenState()
    object Initial : ScannerScreenState()
}
