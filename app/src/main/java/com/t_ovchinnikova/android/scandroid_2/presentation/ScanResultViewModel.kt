package com.t_ovchinnikova.android.scandroid_2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.data.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import kotlinx.coroutines.launch

class ScanResultViewModel : ViewModel() {

    private val codeRepository = CodeRepository.get()

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
}