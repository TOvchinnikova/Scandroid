package com.t_ovchinnikova.android.scandroid_2.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.data.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import kotlinx.coroutines.launch

class ScanningViewModel: ViewModel() {

    private val codeRepository = CodeRepository.get()

    private val _scannerWorkState = MutableLiveData<Boolean>()
    val scannerWorkState: LiveData<Boolean> = _scannerWorkState

    private val _flashState = MutableLiveData<Boolean>()
    val flashState: LiveData<Boolean> = _flashState

    fun setScannerWorkState(state: Boolean) {
        _scannerWorkState.value = state
    }

    fun switchFlash(state: Boolean) {
        _flashState.value = state
    }

    fun addCode(code: Code) {
        viewModelScope.launch {
            codeRepository.addCode(code)
        }
    }
}