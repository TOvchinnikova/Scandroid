package com.t_ovchinnikova.android.scandroid_2.ui.history

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code

sealed class HistoryScreenState {

    object Initial : HistoryScreenState()

    object Loading : HistoryScreenState()

    data class History(val codes: List<Code>, val isSaveBarcodesToHistory: Boolean) : HistoryScreenState()

    data class EmptyHistory(val isSaveBarcodesToHistory: Boolean) : HistoryScreenState()
}
