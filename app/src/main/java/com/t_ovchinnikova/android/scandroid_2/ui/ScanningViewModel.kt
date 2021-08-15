package com.t_ovchinnikova.android.scandroid_2.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScanningViewModel: ViewModel() {

    val scannerWorkState = MutableLiveData<Boolean>()
    //val scannerWorkState: LiveData<Boolean> = _scannerWorkState


    @MainThread
    fun setScannerWorkState(state: Boolean) {
        Log.d("MyLog", "setScannerWorkState $state")
        this.scannerWorkState.value = state
    }

}