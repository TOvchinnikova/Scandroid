package com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase.interactor

import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.repository.CodeListRepository
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase.DeleteAllCodesUseCase

class DeleteAllCodesInteractor(
    private val repository: CodeListRepository
) : DeleteAllCodesUseCase {

    override suspend fun invoke() {
        repository.deleteAllCodes()
    }
}