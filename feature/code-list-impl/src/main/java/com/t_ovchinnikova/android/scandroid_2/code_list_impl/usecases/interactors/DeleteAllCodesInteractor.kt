package com.t_ovchinnikova.android.scandroid_2.code_list_impl.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.code_list_impl.repository.CodeListRepository
import com.t_ovchinnikova.android.scandroid_2.code_list_api.repository.usecases.DeleteAllCodesUseCase

class DeleteAllCodesInteractor(
    private val repository: CodeListRepository
) : DeleteAllCodesUseCase {

    override suspend fun invoke() {
        repository.deleteAllCodes()
    }
}