package com.t_ovchinnikova.android.scandroid_2.ui.scanner

import com.t_ovchinnikova.android.scandroid_2.data.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.domain.Code

sealed class ScannerScreenState {
    data class Scanning(
        val isFlashlightWorks: Boolean,
        val lastScannedCode: Code? = null,
        val settingsData: SettingsData
    ) : ScannerScreenState()
    object Paused : ScannerScreenState()
    object SavingCode : ScannerScreenState()
    object Initial : ScannerScreenState()
}
