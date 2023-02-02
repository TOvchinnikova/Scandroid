package com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.data.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetSettingsUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch

class ScanningViewModel(
    private val addCodeUseCase: AddCodeUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _flashState = MutableLiveData<Boolean>()
    val flashState: LiveData<Boolean> = _flashState

    private val _lastScannedCode = MutableLiveData<Code?>()
    val lastScannedCode: LiveData<Code?>
        get() = _lastScannedCode

    private val scannerWorkStateFlow =
        MutableStateFlow<ScannerWorkState>(ScannerWorkState.ScannerActive)

    private val settingsFlow = getSettingsUseCase.invokeAsync()
        .flowOn(IO)
        .filterNotNull()
        .onEach {
            _flashState.value = it.isFlashlightWhenAppStarts
        }
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

    fun setScannerState(state: ScannerWorkState) {
        scannerWorkStateFlow.value = state
    }

    fun switchFlash() {
        _flashState.value = _flashState.value?.not() ?: false
    }

    fun addCode(code: Code) {
        scannerWorkStateFlow.value = ScannerWorkState.ScanInactive
        _lastScannedCode.value = code
        viewModelScope.launch {
            if (getSettingsUseCase.invoke().isSaveScannedBarcodesToHistory) {
                val idSavedCode = addCodeUseCase(code)
                scannerWorkStateFlow.value =
                    ScannerWorkState.ScanNeedShowResult(code.copy(id = idSavedCode))
            } else {
                scannerWorkStateFlow.value = ScannerWorkState.ScanNeedShowResult(code)
            }
        }
    }

    fun getSettings(): SettingsData? {
        return settingsFlow.value
    }

    fun getScannerWorkStateObservable(): StateFlow<ScannerWorkState> {
        return scannerWorkStateFlow
    }

    sealed class ScannerWorkState {
        object ScannerActive : ScannerWorkState()
        object ScanInactive : ScannerWorkState()
        class ScanNeedShowResult(val scannedCode: Code) : ScannerWorkState()
    }
}