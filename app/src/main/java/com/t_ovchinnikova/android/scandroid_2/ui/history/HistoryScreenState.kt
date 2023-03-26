package com.t_ovchinnikova.android.scandroid_2.ui.history

import com.t_ovchinnikova.android.scandroid_2.domain.Code

sealed class HistoryScreenState {

    object Initial : HistoryScreenState()

    object Loading : HistoryScreenState()

    data class History(val codes: List<Code>) : HistoryScreenState()
}