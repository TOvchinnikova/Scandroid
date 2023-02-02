package com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.ui.scanner.ScannerScreenState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch

class ScanningViewModel(
    private val addCodeUseCase: AddCodeUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _screenStateFlow = MutableStateFlow<ScannerScreenState>(ScannerScreenState.Initial)
    val screenStateFlow: StateFlow<ScannerScreenState> = _screenStateFlow

//    private val _flashState = MutableStateFlow(false)
//    val flashState: StateFlow<Boolean> = _flashState

    private val _lastScannedCode = MutableStateFlow<Code?>(null)
    val lastScannedCode: StateFlow<Code?> = _lastScannedCode

    private val scannerWorkStateFlow =
        MutableStateFlow<ScannerWorkState>(ScannerWorkState.ScannerActive)

    private val settingsFlow = getSettingsUseCase.invokeAsync()
        .filterNotNull()
        .onEach {
            _screenStateFlow.value =
                ScannerScreenState.Scanning(
                    settingsData = it,
                    isFlashlightWorks = it.isFlashlightWhenAppStarts
                )
        }
        .flowOn(IO)
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
        if (screenStateFlow.value is ScannerScreenState.Scanning) {
            //_flashState.value = _flashState.value.not()
            val oldState = screenStateFlow.value as ScannerScreenState.Scanning
            _screenStateFlow.value = oldState.copy(
                isFlashlightWorks = !oldState.isFlashlightWorks
            )
        }
    }

    fun saveCode(code: Code) {
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

    fun getScannerWorkStateObservable(): StateFlow<ScannerWorkState> {
        return scannerWorkStateFlow
    }

    sealed class ScannerWorkState {
        object ScannerActive : ScannerWorkState()
        object ScanInactive : ScannerWorkState()
        class ScanNeedShowResult(val scannedCode: Code) : ScannerWorkState()
    }
}