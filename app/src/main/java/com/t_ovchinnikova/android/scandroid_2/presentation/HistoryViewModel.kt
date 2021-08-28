package com.t_ovchinnikova.android.scandroid_2.presentation

import androidx.lifecycle.ViewModel
import com.t_ovchinnikova.android.scandroid_2.data.CodeRepository

class HistoryViewModel: ViewModel() {

    private val codeRepository = CodeRepository.get()

    val codeListLiveData = codeRepository.getCodes()

    fun deleteCode(codeId: Long) {
        codeRepository.deleteCode(codeId)
    }

}