package com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.DeleteAllCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetCodesUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val deleteCodeUseCase: DeleteCodeUseCase,
    private val deleteAllCodesUseCase: DeleteAllCodesUseCase,
    val getCodesUseCase: GetCodesUseCase,
) : ViewModel() {

    private val codeListFlow = getCodesUseCase()
        .flowOn(IO)
        .filterNotNull()
        .onEach {
            codesHistoryStateFlow.value = CodesHistoryState.ReadyToShow(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val codesHistoryStateFlow =
        MutableStateFlow<CodesHistoryState>(CodesHistoryState.Loading)

    private val _codeDialogShowed = MutableLiveData<Boolean>()
    val codeDialogShowed: LiveData<Boolean> = _codeDialogShowed

    fun deleteCode(codeId: Long) {
        viewModelScope.launch {
            codesHistoryStateFlow.value = CodesHistoryState.Loading
            deleteCodeUseCase(codeId)
        }
    }

    fun deleteAllCodes() {
        viewModelScope.launch {
            codesHistoryStateFlow.value = CodesHistoryState.Loading
            deleteAllCodesUseCase()
        }
    }

    fun showCodeDialog(isShowed: Boolean) {
        _codeDialogShowed.value = isShowed
    }

    fun getCodeListObservable(): StateFlow<List<Code>> {
        return codeListFlow
    }

    fun getCodesHistoryStateObservable(): StateFlow<CodesHistoryState> {
        return codesHistoryStateFlow
    }

    sealed class CodesHistoryState {
        object Loading : CodesHistoryState()
        class ReadyToShow(val codes: List<Code>) : CodesHistoryState()
    }
}