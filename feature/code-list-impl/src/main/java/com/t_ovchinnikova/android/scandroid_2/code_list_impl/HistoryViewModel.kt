package com.t_ovchinnikova.android.scandroid_2.code_list_impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.code_list_api.repository.usecases.DeleteAllCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.usecases.GetCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_ui.EMPTY
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class HistoryViewModel(
    private val deleteCodeUseCase: DeleteCodeUseCase,
    private val deleteAllCodesUseCase: DeleteAllCodesUseCase,
    private val addCodeUseCase: AddCodeUseCase,
    getCodesUseCase: GetCodesUseCase,
    settingsUseCase: GetSettingsUseCase,
    dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val codeListFlow = getCodesUseCase()
        .flowOn(dispatcher)
        .filterNotNull()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    private val searchConditionFlow = MutableStateFlow(EMPTY)

    val codesHistoryStateFlow =
        combine(
            codeListFlow.filterNotNull(),
            settingsUseCase.invokeAsync(),
            searchConditionFlow
        ) { codeList, settings, searchCondition ->
            val filteredList = codeList.filter {
                it.text.contains(searchCondition) ||
                        it.note.contains(searchCondition) ||
                        it.type.name.contains(searchCondition) ||
                        it.format.name.contains(searchCondition)
            }
            if (filteredList.isEmpty()) {
                HistoryScreenState.EmptyHistory(settings.isSaveScannedBarcodesToHistory)
            } else {
                HistoryScreenState.History(
                    filteredList,
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

    fun updateSearchCondition(condition: String) {
        searchConditionFlow.value = condition
    }
}