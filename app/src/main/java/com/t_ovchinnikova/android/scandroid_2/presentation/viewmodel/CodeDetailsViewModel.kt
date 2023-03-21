package com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.ui.code_info.CodeDetailsScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class CodeDetailsViewModel(
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
            CodeDetailsScreenState.CodeDetails(
                it,
                settings.isSaveScannedBarcodesToHistory,
                settings.isSendingNoteWithCode
            )
        } ?: CodeDetailsScreenState.CodeNotFound
    }
        .onStart {
            emit(CodeDetailsScreenState.Loading)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = CodeDetailsScreenState.Initial
        )

    val screenStateFlow: StateFlow<CodeDetailsScreenState> = _screenStateFlow

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