package com.t_ovchinnikova.android.scandroid_2

data class SettingsData(
    val isVibrationOnScan: Boolean,
    val isSaveScannedBarcodesToHistory: Boolean,
    val isFlashlightWhenAppStarts: Boolean,
    val isSendingNoteWithCode: Boolean
)