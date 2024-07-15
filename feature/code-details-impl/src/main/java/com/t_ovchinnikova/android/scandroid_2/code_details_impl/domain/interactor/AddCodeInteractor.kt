package com.t_ovchinnikova.android.scandroid_2.code_details_impl.domain.interactor

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.domain.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddCodeInteractor(
    private val repository: CodeRepository,
    private val dispatcher: CoroutineDispatcher
) : AddCodeUseCase {

    override suspend fun invoke(code: Code): Boolean {
        return withContext(dispatcher) {
            repository.addCode(code)
        }
    }
}