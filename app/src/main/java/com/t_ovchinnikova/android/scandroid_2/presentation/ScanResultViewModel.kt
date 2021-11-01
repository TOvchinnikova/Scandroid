package com.t_ovchinnikova.android.scandroid_2.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.DeleteCodeUseCase
import kotlinx.coroutines.launch

class ScanResultViewModel(
    private val deleteCodeUseCase: DeleteCodeUseCase,
    private val addCodeUseCase: AddCodeUseCase,
) : ViewModel() {

    private val _code = MutableLiveData<Code>()
    val code: LiveData<Code> = _code

    fun updateBarcode(code: Code) {
        _code.value = code
        viewModelScope.launch {
            addCodeUseCase.addCode(code)
        }
    }

    fun deleteBarcode(id: Long) {
        viewModelScope.launch {
            deleteCodeUseCase.deleteCode(id)
        }
    }

    fun editCode(code: Code) {
        _code.value = code
    }
}