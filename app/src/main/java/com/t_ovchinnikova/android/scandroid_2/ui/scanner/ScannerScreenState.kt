package com.t_ovchinnikova.android.scandroid_2.ui.scanner

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.SettingsData

sealed class ScannerScreenState {
    data class Scanning(
        val isFlashlightWorks: Boolean,
        val lastScannedCode: Code? = null,
        val settingsData: SettingsData? = null
    ) : ScannerScreenState()
    object SavingCode : ScannerScreenState()
    object Initial : ScannerScreenState()
}
