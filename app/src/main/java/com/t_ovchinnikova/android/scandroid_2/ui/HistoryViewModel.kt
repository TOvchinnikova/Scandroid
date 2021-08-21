package com.t_ovchinnikova.android.scandroid_2.ui

import androidx.lifecycle.ViewModel
import com.t_ovchinnikova.android.scandroid_2.CodeRepository

class HistoryViewModel: ViewModel() {

    private val codeRepository = CodeRepository.get()

    val codeListLiveData = codeRepository.getCodes()


}