package com.t_ovchinnikova.android.scandroid_2.scanner_impl.ui

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_mvi.UiState
import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData

data class ScannerScreenUiState(
    val isFlashlightWorks: Boolean = false,
    val lastScannedCode: Code? = null,
    val settingsData: SettingsData? = null,
    val isLoading: Boolean = true,
    val isSavingCode: Boolean = false
) : UiState
