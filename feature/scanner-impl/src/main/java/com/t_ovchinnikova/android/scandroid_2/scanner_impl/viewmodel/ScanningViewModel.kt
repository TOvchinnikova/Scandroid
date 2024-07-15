package com.t_ovchinnikova.android.scandroid_2.scanner_impl.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_mvi.BaseViewModel
import com.t_ovchinnikova.android.scandroid_2.core_utils.vibrate
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.domain.usecase.GetScannedCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.model.mvi.ScannerScreenUiAction
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.model.mvi.ScannerScreenUiSideEffect
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.model.mvi.ScannerScreenUiState
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScanningViewModel(
    private val context: Application,
    private val addCodeUseCase: AddCodeUseCase,
    getSettingsUseCase: GetSettingsUseCase,
    private val dispatcher: CoroutineDispatcher,
    getScannedCodeUseCase: GetScannedCodeUseCase
) : BaseViewModel<ScannerScreenUiState, ScannerScreenUiAction>() {

    private val mutableUiSideEffect: MutableSharedFlow<ScannerScreenUiSideEffect> by lazy {
        MutableSharedFlow(
            extraBufferCapacity = 1,
        )
    }
    val uiSideEffect: SharedFlow<ScannerScreenUiSideEffect> = mutableUiSideEffect.asSharedFlow()

    private val settingsFlow = getSettingsUseCase.invokeAsync()
        .onEach {
            updateState {
                copy(
                    isFlashlightWorks = it.isFlashlightWhenAppStarts,
                    settingsData = it,
                    isLoading = false
                )
            }
        }
        .flowOn(dispatcher)
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(),
            initialValue = null
        )

    private val scannedCodeFlow: SharedFlow<Code> = getScannedCodeUseCase.invoke()
        .onEach {
            if (uiState.value.lastScannedCode?.text != it.text) {
                onScannedCode(it)
            }
        }
        .flowOn(dispatcher)
        .shareIn(
            scope = viewModelScope,
            started = WhileSubscribed()
        )

    init {
        viewModelScope.launch {
            settingsFlow.collect()
        }

        viewModelScope.launch {
            scannedCodeFlow.collect()
        }
    }

    override fun getInitialState(): ScannerScreenUiState = ScannerScreenUiState()

    override fun onAction(action: ScannerScreenUiAction) {
        when (action) {
            ScannerScreenUiAction.SwitchFlash -> switchFlash()
            is ScannerScreenUiAction.CodeScanned -> onScannedCode(action.code)
        }
    }

    private fun switchFlash() {
        updateState {
            copy(isFlashlightWorks = !isFlashlightWorks)
        }
    }

    private fun onScannedCode(code: Code) {
        viewModelScope.launch(dispatcher) {
            updateState {
                copy(
                    isSavingCode = true,
                    lastScannedCode = code
                )
            }
            if (uiState.value.settingsData?.isVibrationOnScan == true) {
                context.vibrate()
            }
            addCodeUseCase(code)
            updateState {
                copy(isSavingCode = false)
            }
            mutableUiSideEffect.emit(ScannerScreenUiSideEffect.OpenCodeDetails(code.id))
        }
    }
}