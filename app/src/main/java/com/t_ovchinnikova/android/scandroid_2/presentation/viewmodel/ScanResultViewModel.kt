package com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t_ovchinnikova.android.scandroid_2.data.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetSettingsUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ScanResultViewModel(
    codeId: Long,
    private val deleteCodeUseCase: DeleteCodeUseCase,
    private val addCodeUseCase: AddCodeUseCase,
    getCodeUseCase: GetCodeUseCase,
    getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _code = getCodeUseCase(codeId)
        .flowOn(IO)
        .filterNotNull()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )
    val code: StateFlow<Code?> = _code

    private val settingsFlow = getSettingsUseCase.invokeAsync()
        .flowOn(IO)
        .filterNotNull()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    init {
        viewModelScope.launch(IO) {
            settingsFlow.collect()
        }
    }

    fun updateBarcode(code: Code) {
        viewModelScope.launch {
            addCodeUseCase(code)
        }
    }

    fun deleteBarcode(id: Long) {
        viewModelScope.launch {
            deleteCodeUseCase(id)
        }
    }

    fun getSettings(): SettingsData? {
        return settingsFlow.value
    }
}