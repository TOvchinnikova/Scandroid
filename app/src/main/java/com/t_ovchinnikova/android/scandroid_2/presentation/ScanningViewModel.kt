package com.t_ovchinnikova.android.scandroid_2.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.data.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScanningViewModel: ViewModel() {

    private val codeRepository = CodeRepository.get()

    private val _scannerWorkState = MutableLiveData<Boolean>()
    val scannerWorkState: LiveData<Boolean> = _scannerWorkState

    private val _flashState = MutableLiveData<Boolean>()
    val flashState: LiveData<Boolean> = _flashState

    private val _newCode = MutableLiveData<Code?>()
    val newCode: LiveData<Code?>
        get() = _newCode

    fun setScannerWorkState(state: Boolean) {
        _scannerWorkState.value = state
        if (state) _newCode.value = null
    }

    fun switchFlash(state: Boolean) {
        _flashState.value = state
    }

    fun addCode(code: Code) {
        viewModelScope.launch {
            val id = codeRepository.addCode(code)
            code.id = id
            _newCode.value = code
        }
    }
}