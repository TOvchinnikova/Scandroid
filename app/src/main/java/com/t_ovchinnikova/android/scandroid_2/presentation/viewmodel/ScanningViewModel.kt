package com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.ui.scanner.ScannerScreenState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch

class ScanningViewModel(
    private val addCodeUseCase: AddCodeUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _screenStateFlow = MutableStateFlow<ScannerScreenState>(ScannerScreenState.Initial)
    val screenStateFlow: StateFlow<ScannerScreenState> = _screenStateFlow

    private val _flashState = MutableStateFlow(false)

    private val _lastScannedCode = MutableStateFlow<Code?>(null)
    val lastScannedCode: StateFlow<Code?> = _lastScannedCode

    private val scannerWorkStateFlow =
        MutableStateFlow<ScannerWorkState>(ScannerWorkState.ScannerActive)

    private val settingsFlow = getSettingsUseCase.invokeAsync()
        .onEach {
            _flashState.value = it.isFlashlightWhenAppStarts
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
        _flashState.value = !_flashState.value
        if (screenStateFlow.value is ScannerScreenState.Scanning) {
            val oldState = screenStateFlow.value as ScannerScreenState.Scanning
            _screenStateFlow.value = oldState.copy(
                isFlashlightWorks = !oldState.isFlashlightWorks
            )
        }
    }

    fun saveCode(code: Code) {
        _lastScannedCode.value = code
        _screenStateFlow.value = ScannerScreenState.SavingCode
        viewModelScope.launch {
            val idSavedCode = if (getSettingsUseCase.invoke().isSaveScannedBarcodesToHistory) {
                code.copy(id = addCodeUseCase(code))
            } else {
                code
            }
//            _screenStateFlow.value = ScannerScreenState.Scanning(
//                isFlashlightWorks = _flashState.value,
//                lastScannedCode = code,
//                settingsData = settingsFlow.value
//            )
            _screenStateFlow.value = ScannerScreenState.Paused
        }
    }

    sealed class ScannerWorkState {
        object ScannerActive : ScannerWorkState()
        object ScanInactive : ScannerWorkState()
        class ScanNeedShowResult(val scannedCode: Code) : ScannerWorkState()
    }
}