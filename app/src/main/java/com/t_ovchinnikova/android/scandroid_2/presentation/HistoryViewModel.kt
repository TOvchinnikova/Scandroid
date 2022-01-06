package com.t_ovchinnikova.android.scandroid_2.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.DeleteAllCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetCodesUseCase
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val deleteCodeUseCase: DeleteCodeUseCase,
    private val deleteAllCodesUseCase: DeleteAllCodesUseCase,
    val getCodesUseCase: GetCodesUseCase,
) : ViewModel() {

    val codeListLiveData = getCodesUseCase.getCodes()

    private val _codeDialogShowed = MutableLiveData<Boolean>()
    val codeDialogShowed: LiveData<Boolean> = _codeDialogShowed

    fun deleteCode(codeId: Long) {
        viewModelScope.launch {
            deleteCodeUseCase.deleteCode(codeId)
        }
    }

    fun deleteAllCodes() {
        viewModelScope.launch {
            deleteAllCodesUseCase.deleteAllCodes()
        }
    }

    fun showCodeDialog(isShowed: Boolean) {
        _codeDialogShowed.value = isShowed
    }
}