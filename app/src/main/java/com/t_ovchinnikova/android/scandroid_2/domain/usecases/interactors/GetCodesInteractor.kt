package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import androidx.lifecycle.LiveData
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.data.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetCodesUseCase

class GetCodesInteractor(
    private val repository: CodeRepository
) : GetCodesUseCase {

    override fun invoke(): LiveData<List<Code>> {
        return repository.getCodes()
    }
}