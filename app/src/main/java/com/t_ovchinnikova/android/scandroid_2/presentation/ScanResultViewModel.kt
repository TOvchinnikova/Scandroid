package com.t_ovchinnikova.android.scandroid_2.presentation

import android.app.Application
import android.app.SearchManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.data.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import kotlinx.coroutines.launch

class ScanResultViewModel(application: Application) : AndroidViewModel(application) {

    private val codeRepository = CodeRepository.get()
    private val context = application

    fun updateBarcode(code: Code) {
        viewModelScope.launch {
            codeRepository.addCode(code)
        }
    }

    fun deleteBarcode(id: Long) {
        viewModelScope.launch {
            codeRepository.deleteCode(id)
        }
    }

    fun copyToClipboard(text: String) {
        val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("code text", text)
        clipboard.setPrimaryClip(clip)
    }

    fun searchWeb(queryText: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, queryText)
        }
        startActivity(intent)
    }

    fun sendText(text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(intent)
    }

    private fun startActivity(intent: Intent) {
        intent.apply {
            flags = flags or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}