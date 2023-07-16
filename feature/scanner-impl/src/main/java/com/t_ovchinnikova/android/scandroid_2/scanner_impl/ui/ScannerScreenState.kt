package com.t_ovchinnikova.android.scandroid_2.scanner_impl.ui

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData

sealed class ScannerScreenState {
    data class Scanning(
        val isFlashlightWorks: Boolean,
        val lastScannedCode: Code? = null,
        val settingsData: com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData? = null
    ) : ScannerScreenState()
    object SavingCode : ScannerScreenState()
    object Initial : ScannerScreenState()
}
