package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase.DeleteAllCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase.GetCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.CodeUiModel
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi.HistoryUiAction
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi.HistoryUiState
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_mvi.BaseViewModel
import com.t_ovchinnikova.android.scandroid_2.core_ui.EMPTY
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.util.UUID

class HistoryViewModel(
    private val deleteCodeUseCase: DeleteCodeUseCase,
    private val deleteAllCodesUseCase: DeleteAllCodesUseCase,
    private val addCodeUseCase: AddCodeUseCase,
    getCodesUseCase: GetCodesUseCase,
    private val dispatcher: CoroutineDispatcher
) : BaseViewModel<HistoryUiState, HistoryUiAction>() {

    private val searchConditionFlow = MutableStateFlow(EMPTY)

    override fun getInitialState(): HistoryUiState = HistoryUiState(isLoading = true)

    override fun onAction(action: HistoryUiAction) {
        when (action) {
            HistoryUiAction.DeleteAllCodes -> deleteAllCodes()
            is HistoryUiAction.DeleteCode -> deleteCode(action.id)
            is HistoryUiAction.ToggleFavourite -> toggleFavourite(action.code)
            is HistoryUiAction.UpdateSearchCondition -> updateSearchCondition(action.condition)
            is HistoryUiAction.LongClickItem -> onLongClickItem(action.codeId)
            HistoryUiAction.CancelChecking -> onCancelChecking()
            is HistoryUiAction.CheckCode -> onCheckCode(action.codeId)
            HistoryUiAction.ShowDeleteDialog -> updateState { copy(isVisibleDeleteDialog = true) }
            HistoryUiAction.HideDeleteDialog -> updateState { copy(isVisibleDeleteDialog = false) }
        }
    }

    init {
        combine(
            getCodesUseCase(),
            searchConditionFlow
        ) { codeList, searchCondition ->
            val filteredList = codeList.filter {
                it.text.contains(searchCondition) ||
                        it.note.contains(searchCondition) ||
                        it.type.name.contains(searchCondition) ||
                        it.format.name.contains(searchCondition)
            }.map { CodeUiModel(code = it, isChecked = false) }
            updateState {
                copy(codes = filteredList, isLoading = false)
            }
        }
            .flowOn(dispatcher)
            .launchIn(viewModelScope)
    }

    private fun deleteCode(codeId: UUID) {
        viewModelScope.launch {
            deleteCodeUseCase(codeId)
        }
    }

    private fun deleteAllCodes() {
        viewModelScope.launch {
            deleteAllCodesUseCase()
        }
    }

    private fun toggleFavourite(code: Code) {
        viewModelScope.launch {
            addCodeUseCase(
                code.copy(
                    isFavorite = !code.isFavorite
                )
            )
        }
    }

    private fun updateSearchCondition(condition: String) {
        searchConditionFlow.value = condition
    }

    private fun onCheckCode(codeId: UUID) {
        updateState {
            copy(
                codes = codes.map {
                    if (it.code.id == codeId) {
                        CodeUiModel(it.code, !it.isChecked)
                    } else {
                        it
                    }
                }
            )
        }
    }

    private fun onCancelChecking() {
        updateState {
            copy(
                isVisibleCheckBox = false,
                codes = codes.map {
                    CodeUiModel(it.code, false)
                }
            )
        }
    }

    private fun onLongClickItem(codeId: UUID) {
        updateState {
            copy(
                isVisibleCheckBox = true,
                codes = codes.map {
                    if (it.code.id == codeId) {
                        CodeUiModel(it.code, !it.isChecked)
                    } else {
                        it
                    }
                }
            )
        }
    }
}