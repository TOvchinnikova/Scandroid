package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.data.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.DeleteCodeUseCase

class DeleteCodeInteractor(
    private val repository: CodeRepository
) : DeleteCodeUseCase {

    override suspend fun invoke(codeId: Long) {
        repository.deleteCode(codeId)
    }
}