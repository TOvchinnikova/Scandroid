package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import androidx.lifecycle.LiveData
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.CodeRepository

class GetCodesWithFilterUseCase(private val repository: CodeRepository) {

    fun getCodesWithFilter(filterText: String): LiveData<List<Code>> {
        return repository.getCodesWithFilter(filterText)
    }
}