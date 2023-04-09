package com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.*
import com.t_ovchinnikova.android.scandroid_2.ui.history.HistoryScreenState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class HistoryViewModel(
    private val deleteCodeUseCase: DeleteCodeUseCase,
    private val deleteAllCodesUseCase: DeleteAllCodesUseCase,
    private val addCodeUseCase: AddCodeUseCase,
    getCodesUseCase: GetCodesUseCase,
    settingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val codeListFlow = getCodesUseCase()
        .flowOn(IO)
        .filterNotNull()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    val codesHistoryStateFlow =
        combine(
            codeListFlow.filterNotNull(),
            settingsUseCase.invokeAsync()
        ) { codeList, settings ->
            if (codeList.isEmpty()) {
                HistoryScreenState.EmptyHistory(settings.isSaveScannedBarcodesToHistory)
            } else {
                HistoryScreenState.History(
                    codeList,
                    settings.isSaveScannedBarcodesToHistory
                ) as HistoryScreenState
            }
        }
            .onStart {
                emit(HistoryScreenState.Loading)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = HistoryScreenState.Initial
            )

    fun deleteCode(codeId: UUID) {
        viewModelScope.launch {
            deleteCodeUseCase(codeId)
        }
    }

    fun deleteAllCodes() {
        viewModelScope.launch {
            deleteAllCodesUseCase()
        }
    }

    fun toggleFavourite(code: Code) {
        viewModelScope.launch {
            addCodeUseCase(
                code.copy(
                    isFavorite = !code.isFavorite
                )
            )
        }
    }
}