package com.t_ovchinnikova.android.scandroid_2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScanningViewModel: ViewModel() {

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

}