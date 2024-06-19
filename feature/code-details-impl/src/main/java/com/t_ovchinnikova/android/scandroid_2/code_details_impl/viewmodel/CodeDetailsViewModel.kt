package com.t_ovchinnikova.android.scandroid_2.code_details_impl.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.ui.CodeDetailsUiAction
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.ui.CodeDetailsUiState
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_mvi.BaseViewModel
import com.t_ovchinnikova.android.scandroid_2.core_utils.copyToClipboard
import com.t_ovchinnikova.android.scandroid_2.core_utils.searchWeb
import com.t_ovchinnikova.android.scandroid_2.core_utils.shareText
import com.t_ovchinnikova.android.scandroid_2.core_utils.toStringRes
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.UUID

class CodeDetailsViewModel(
    private val codeId: UUID,
    private val context: Context,
    private val deleteCodeUseCase: DeleteCodeUseCase,
    private val addCodeUseCase: AddCodeUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val getCodeUseCase: GetCodeUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : BaseViewModel<CodeDetailsUiState, CodeDetailsUiAction>() {

    init {
        loadData()
    }

    override fun getInitialState(): CodeDetailsUiState = CodeDetailsUiState()

    override fun onAction(action: CodeDetailsUiAction) {
        when (action) {
            CodeDetailsUiAction.DeleteBarcode -> {
                deleteBarcode()
            }

            CodeDetailsUiAction.FavouriteClicked -> {
                onFavouriteClicked()
            }

            is CodeDetailsUiAction.NoteChanged -> {
                onNoteChanged(action.note)
            }

            CodeDetailsUiAction.CopyCodeValueToClipboard -> {
                context.copyToClipboard(uiState.value.code?.text ?: "")
            }

            CodeDetailsUiAction.SearchOnWeb -> {
                context.searchWeb(uiState.value.code?.text ?: "")
            }

            CodeDetailsUiAction.ShareCodeValue -> {
                shareCodeValue()
            }
        }
    }

    private fun loadData() {
        combine(
            getCodeUseCase.invokeAsync(codeId),
            getSettingsUseCase.invokeAsync()
        ) { code, settings ->
            updateState {
                copy(
                    toolbarTitle = code?.let { context.getString(it.format.toStringRes()) } ?: "",
                    code = code,
                    isSendingNoteWithCode = settings.isSendingNoteWithCode,
                    isLoading = false
                )
            }
        }
            .onStart {
                updateState {
                    copy(isLoading = true)
                }
            }
            .flowOn(dispatcher)
            .launchIn(viewModelScope)
    }

    private fun shareCodeValue() {
        uiState.value.code?.let { code ->
            val message =
                if (code.note.isNotBlank() && uiState.value.isSendingNoteWithCode)
                    code.text + '\n' + code.note
                else
                    code.text
            context.shareText(message)
        }
    }

    private fun onNoteChanged(note: String) {
        uiState.value.code?.let {
            updateBarcode(it.copy(note = note))
        }
    }

    private fun onFavouriteClicked() {
        uiState.value.code?.let {
            updateBarcode(it.copy(isFavorite = !it.isFavorite))
        }
    }

    private fun updateBarcode(code: Code) {
        viewModelScope.launch(dispatcher) {
            addCodeUseCase(code)
        }
    }

    private fun deleteBarcode() {
        viewModelScope.launch(dispatcher) {
            uiState.value.code?.let {
                deleteCodeUseCase(it.id)
            }

        }
    }
}