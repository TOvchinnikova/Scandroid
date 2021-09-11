package com.t_ovchinnikova.android.scandroid_2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.data.CodeRepository
import kotlinx.coroutines.launch

class HistoryViewModel: ViewModel() {

    private val codeRepository = CodeRepository.get()

    val codeListLiveData = codeRepository.getCodes()

    fun deleteCode(codeId: Long) {
        viewModelScope.launch {
            codeRepository.deleteCode(codeId)
        }
    }
}