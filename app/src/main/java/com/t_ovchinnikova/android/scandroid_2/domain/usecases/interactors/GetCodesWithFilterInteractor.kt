package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import androidx.lifecycle.LiveData
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.data.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetCodesWithFilterUseCase

class GetCodesWithFilterInteractor(
    private val repository: CodeRepository
) :
    GetCodesWithFilterUseCase {

    override fun invoke(filterText: String): LiveData<List<Code>> {
        return repository.getCodesWithFilter(filterText)
    }
}