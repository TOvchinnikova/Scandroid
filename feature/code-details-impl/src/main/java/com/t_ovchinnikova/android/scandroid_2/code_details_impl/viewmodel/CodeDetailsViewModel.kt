package com.t_ovchinnikova.android.scandroid_2.code_details_impl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.ui.CodeDetailsScreenState
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class CodeDetailsViewModel(
    codeId: UUID,
    private val deleteCodeUseCase: DeleteCodeUseCase,
    private val addCodeUseCase: AddCodeUseCase,
    dispatcher: CoroutineDispatcher,
    getCodeUseCase: GetCodeUseCase,
    getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val codeFlow = getCodeUseCase.invokeAsync(codeId)
        .flowOn(dispatcher)
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
                settings.isSendingNoteWithCode
            )
        } ?: CodeDetailsScreenState.CodeNotFound
    }
        .onStart {
            emit(CodeDetailsScreenState.Loading)
        }
        .flowOn(dispatcher)
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