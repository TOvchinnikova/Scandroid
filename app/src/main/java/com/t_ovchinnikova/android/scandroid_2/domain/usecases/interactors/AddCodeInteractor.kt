package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase

class AddCodeInteractor(
    private val repository: CodeRepository
) : AddCodeUseCase {

    override suspend fun invoke(code: Code): Boolean {
        return repository.addCode(code)
    }
}