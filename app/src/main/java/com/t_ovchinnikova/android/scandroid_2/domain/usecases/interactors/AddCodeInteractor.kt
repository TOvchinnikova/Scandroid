package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.AddCodeUseCase

class AddCodeInteractor(private val repository: CodeRepository) : AddCodeUseCase {

    override suspend fun invoke(code: Code): Long {
        return repository.addCode(code)
    }
}