package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.data.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.DeleteAllCodesUseCase

class DeleteAllCodesInteractor(
    private val repository: CodeRepository
) : DeleteAllCodesUseCase {

    override suspend fun invoke() {
        repository.deleteAllCodes()
    }
}