package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase
import java.util.*

class DeleteCodeInteractor(
    private val repository: com.t_ovchinnikova.android.scandroid_2.core_domain.repository.CodeRepository
) : com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase {

    override suspend fun invoke(codeId: UUID) {
        repository.deleteCode(codeId)
    }
}