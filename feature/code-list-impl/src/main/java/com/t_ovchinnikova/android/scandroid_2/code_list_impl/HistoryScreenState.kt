package com.t_ovchinnikova.android.scandroid_2.code_list_impl

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code

sealed class HistoryScreenState {

    object Initial : HistoryScreenState()

    object Loading : HistoryScreenState()

    data class History(val codes: List<Code>) : HistoryScreenState()

    object EmptyHistory : HistoryScreenState()
}
