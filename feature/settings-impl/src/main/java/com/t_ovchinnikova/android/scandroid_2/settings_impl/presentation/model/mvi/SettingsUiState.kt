package com.t_ovchinnikova.android.scandroid_2.settings_impl.presentation.model.mvi

import com.t_ovchinnikova.android.scandroid_2.core_mvi.UiState
import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData

data class SettingsUiState(
    val isLoading: Boolean = true,
    val settings: SettingsData? = null,
    val isError: Boolean = false
) : UiState
