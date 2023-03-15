package com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.ui.code_info.CodeInfoScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class CodeInfoViewModel(
    codeId: UUID,
    private val deleteCodeUseCase: DeleteCodeUseCase,
    private val addCodeUseCase: AddCodeUseCase,
    getCodeUseCase: GetCodeUseCase,
    getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _screenStateFlow = combine(
        getCodeUseCase(codeId),
        getSettingsUseCase.invokeAsync()
    ) { code, settings ->
        code?.let {
            CodeInfoScreenState.CodeInfo(
                it,
                settings.isSaveScannedBarcodesToHistory,
                settings.isSendingNoteWithCode
            )
        } ?: CodeInfoScreenState.CodeNotFound
    }
        .onStart {
            emit(CodeInfoScreenState.Loading)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = CodeInfoScreenState.Initial
        )

    val screenStateFlow: StateFlow<CodeInfoScreenState> = _screenStateFlow

    fun updateBarcode(code: Code) {
        viewModelScope.launch {
            addCodeUseCase(code)
        }
    }

    fun deleteBarcode(id: UUID) {
        viewModelScope.launch {
            deleteCodeUseCase(id)
        }
    }
}