package com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.SettingsData
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetSettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ScanResultViewModel(
    private val deleteCodeUseCase: DeleteCodeUseCase,
    private val addCodeUseCase: AddCodeUseCase,
    getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _code = MutableLiveData<Code>()
    val code: LiveData<Code> = _code

    private val settingsFlow = getSettingsUseCase()
        .flowOn(Dispatchers.IO)
        .filterNotNull()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsFlow.collect()
        }
    }

    fun updateBarcode(code: Code) {
        _code.value = code
        viewModelScope.launch {
            addCodeUseCase(code)
        }
    }

    fun deleteBarcode(id: Long) {
        viewModelScope.launch {
            deleteCodeUseCase(id)
        }
    }

    fun editCode(code: Code) {
        _code.value = code
    }

    fun getSettings(): SettingsData? {
        return settingsFlow.value
    }
}