package com.t_ovchinnikova.android.scandroid_2.code_details_impl.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.code_details_api.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase

class AddCodeInteractor(
    private val repository: CodeRepository
) : AddCodeUseCase {

    override suspend fun invoke(code: Code): Boolean {
        return repository.addCode(code)
    }
}