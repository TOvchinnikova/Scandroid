package com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.DeleteAllCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.ui.history.HistoryScreenState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val deleteCodeUseCase: DeleteCodeUseCase,
    private val deleteAllCodesUseCase: DeleteAllCodesUseCase,
    private val addCodeUseCase: AddCodeUseCase,
    val getCodesUseCase: GetCodesUseCase,
) : ViewModel() {

    private val codeListFlow = getCodesUseCase()
        .flowOn(IO)
        .filterNotNull()
        .onEach {
            _codesHistoryStateFlow.value = HistoryScreenState.History(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val _codesHistoryStateFlow =
        MutableStateFlow<HistoryScreenState>(HistoryScreenState.Initial)

    val codesHistoryStateFlow: StateFlow<HistoryScreenState> = _codesHistoryStateFlow

    init {
        viewModelScope.launch {
            codeListFlow.collect()
        }
    }

    fun deleteCode(codeId: Long) {
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