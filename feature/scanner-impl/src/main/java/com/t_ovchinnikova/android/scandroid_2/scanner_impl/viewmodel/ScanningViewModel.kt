package com.t_ovchinnikova.android.scandroid_2.scanner_impl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.ui.ScannerScreenState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class ScanningViewModel(
    private val addCodeUseCase: AddCodeUseCase,
    getSettingsUseCase: GetSettingsUseCase,
    dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _screenStateFlow = MutableStateFlow<ScannerScreenState>(ScannerScreenState.Initial)
    val screenStateFlow: StateFlow<ScannerScreenState> = _screenStateFlow

    private val _flashState = MutableStateFlow(false)

    private val _lastScannedCode = MutableStateFlow<Code?>(null) //todo от этого надо избавляться
    val lastScannedCode: StateFlow<Code?> = _lastScannedCode.asStateFlow()

    private val settingsFlow = getSettingsUseCase.invokeAsync()
        .onEach {
            _flashState.value = it.isFlashlightWhenAppStarts
            _screenStateFlow.value =
                ScannerScreenState.Scanning(
                    settingsData = it,
                    isFlashlightWorks = it.isFlashlightWhenAppStarts
                )
        }
        .flowOn(dispatcher)
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(),
            initialValue = null
        )

    init {
        viewModelScope.launch {
            settingsFlow.collect()
        }
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

    fun handleCode(code: Code, onScanListener: (codeId: UUID) -> Unit) {
        _lastScannedCode.value = code
        _screenStateFlow.value = ScannerScreenState.SavingCode
        viewModelScope.launch {
            addCodeUseCase(code)
            onScanListener(code.id)
            _screenStateFlow.value = ScannerScreenState.Scanning(
                isFlashlightWorks = _flashState.value,
                lastScannedCode = code,
                settingsData = settingsFlow.value
            )
        }
    }
}