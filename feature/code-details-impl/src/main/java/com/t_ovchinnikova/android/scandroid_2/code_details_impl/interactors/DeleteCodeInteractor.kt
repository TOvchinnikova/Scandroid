package com.t_ovchinnikova.android.scandroid_2.code_details_impl.interactors

import com.t_ovchinnikova.android.scandroid_2.code_details_api.repository.CodeRepository
import java.util.*

class DeleteCodeInteractor(
    private val repository: CodeRepository
) : com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase {

    override suspend fun invoke(codeId: UUID) {
        repository.deleteCode(codeId)
    }
}