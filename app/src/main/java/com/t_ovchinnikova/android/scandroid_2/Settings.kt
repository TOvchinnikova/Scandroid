package com.t_ovchinnikova.android.scandroid_2

import android.content.Context

private const val SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME"
private const val VIBRATE = "vibrate"
private const val SAVE_SCANNED_BARCODES_TO_HISTORY = "save_scanned_barcodes_to_history"
private const val FLASH = "flash"
private const val SEND_NOTE = "sending note"

class Settings(private val context: Context) {

    private val sharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    var vibrate: Boolean
        get() = get(VIBRATE, true)
        set(value) = set(VIBRATE, value)

    var saveScannedBarcodesToHistory: Boolean
        get() = get(SAVE_SCANNED_BARCODES_TO_HISTORY, true)
        set(value) = set(SAVE_SCANNED_BARCODES_TO_HISTORY, value)

    var flash: Boolean
        get() = get(FLASH)
        set(value) = set(FLASH, value)

    var sendingNote: Boolean
        get() = get(SEND_NOTE)
        set(value) = set(SEND_NOTE, value)

    private fun get(key: String, default: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    private fun set(key: String, value: Boolean) {
        sharedPreferences.edit()
            .putBoolean(key, value)
            .apply()
    }

}