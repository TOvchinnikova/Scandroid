package com.t_ovchinnikova.android.scandroid_2.core_mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State : UiState, Action : UiAction> : ViewModel() {

    private val mutableUiState: MutableStateFlow<State> by lazy { MutableStateFlow(getInitialState()) }
    val uiState: StateFlow<State> by lazy { mutableUiState.asStateFlow() }

    abstract fun getInitialState(): State

    abstract fun onAction(action: Action)

    fun updateState(modifier: State.() -> State) {
        mutableUiState.update { it.modifier() }
    }
}