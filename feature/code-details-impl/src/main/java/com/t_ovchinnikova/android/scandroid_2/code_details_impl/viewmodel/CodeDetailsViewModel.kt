package com.t_ovchinnikova.android.scandroid_2.code_details_impl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.ui.CodeDetailsScreenState
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
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

    private val codeFlow = getCodeUseCase.invokeAsync(codeId)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    val screenStateFlow = combine(
        codeFlow,
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