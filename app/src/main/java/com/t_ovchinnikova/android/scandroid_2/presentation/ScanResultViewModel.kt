package com.t_ovchinnikova.android.scandroid_2.presentation

import androidx.lifecycle.ViewModel
import com.t_ovchinnikova.android.scandroid_2.data.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.Code

class ScanResultViewModel : ViewModel() {

    private val codeRepository = CodeRepository.get()

    fun updateBarcode(code: Code) {
        codeRepository.updateCode(code)
    }

    fun deleteBarcode(id: Long) {
        codeRepository.deleteCode(id)
    }

}