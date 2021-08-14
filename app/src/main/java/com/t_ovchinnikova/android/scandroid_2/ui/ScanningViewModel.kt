package com.t_ovchinnikova.android.scandroid_2.ui

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScanningViewModel: ViewModel() {

    private val _scannerWorkState = MutableLiveData<Boolean>()
    val scannerWorkState: LiveData<Boolean> = _scannerWorkState

    fun setScannerWorkState(state: Boolean) {
        Log.d("MyLog", "setScannerWorkState $state")
        _scannerWorkState.value = state
    }

}