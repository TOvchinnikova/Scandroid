package com.t_ovchinnikova.android.scandroid_2.core_platform_utils

import android.app.SearchManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

fun Context.vibrate() {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = this
            .getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(350, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(350)
    }
}

val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)

fun Context.shareText(
    text: String,
    note: String,
    isSendingNoteWithCode: Boolean
) {
    val message =
        if (note.isNotBlank() && isSendingNoteWithCode)
            text + '\n' + note
        else
            text
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    this.startActivity(shareIntent)
}

fun Context.searchWeb(
    queryText: String
) {
    val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
        putExtra(SearchManager.QUERY, queryText)
    }
    val shareIntent = Intent.createChooser(intent, null)
    this.startActivity(shareIntent)
}

fun Context.copyToClipboard(
    text: String
) {
    val clipboard =
        this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip: ClipData = ClipData.newPlainText("code text", text)
    clipboard.setPrimaryClip(clip)
}