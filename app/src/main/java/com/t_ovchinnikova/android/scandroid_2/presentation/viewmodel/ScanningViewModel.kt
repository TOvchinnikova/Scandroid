package com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.SettingsData
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetSettingsUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScanningViewModel(
    private val addCodeUseCase: AddCodeUseCase,
    getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _scannerWorkState = MutableLiveData<Boolean>()
    val scannerWorkState: LiveData<Boolean> = _scannerWorkState

    private val _flashState = MutableLiveData<Boolean>()
    val flashState: LiveData<Boolean> = _flashState

    private val _newCode = MutableLiveData<Code?>()
    val newCode: LiveData<Code?>
        get() = _newCode

    private val settingsFlow = getSettingsUseCase()
        .flowOn(IO)
        .filterNotNull()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(),
            initialValue = null
        )

    init {
        viewModelScope.launch(IO) {
            settingsFlow.collect()
        }
    }

    fun setScannerWorkState(state: Boolean) {
        _scannerWorkState.value = state
        if (state) _newCode.value = null
    }

    fun switchFlash(state: Boolean) {
        _flashState.value = state
    }

    fun addCode(code: Code, isSave: Boolean) {
        if (isSave) {
            viewModelScope.launch {
                val id = addCodeUseCase(code)
                code.id = id
                _newCode.value = code
            }
        } else {
            _newCode.value = code
        }
    }

    fun getSettings(): SettingsData? {
        return settingsFlow.value
    }
}