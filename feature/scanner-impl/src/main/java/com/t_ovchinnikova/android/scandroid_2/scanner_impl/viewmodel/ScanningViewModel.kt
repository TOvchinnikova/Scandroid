package com.t_ovchinnikova.android.scandroid_2.scanner_impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_mvi.BaseViewModel
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.ui.ScannerScreenUiAction
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.ui.ScannerScreenUiSideEffect
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.ui.ScannerScreenUiState
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScanningViewModel(
    private val addCodeUseCase: AddCodeUseCase,
    getSettingsUseCase: GetSettingsUseCase,
    dispatcher: CoroutineDispatcher
) : BaseViewModel<ScannerScreenUiState, ScannerScreenUiAction>() {

    private val mutableUiSideEffect: MutableSharedFlow<ScannerScreenUiSideEffect> by lazy {
        MutableSharedFlow(
            extraBufferCapacity = 1,
        )
    }
    val uiSideEffect: SharedFlow<ScannerScreenUiSideEffect> = mutableUiSideEffect.asSharedFlow()

    override fun getInitialState(): ScannerScreenUiState = ScannerScreenUiState()

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

    init {
        viewModelScope.launch {
            settingsFlow.collect()
        }
    }

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
        viewModelScope.launch(Dispatchers.IO) {
            updateState {
                copy(
                    isSavingCode = true,
                    lastScannedCode = code
                )
            }
            if (uiState.value.settingsData?.isVibrationOnScan == true) {
                mutableUiSideEffect.emit(ScannerScreenUiSideEffect.Vibrate)
            }
            addCodeUseCase(code)
            updateState {
                copy(isSavingCode = false)
            }
        }
    }
}