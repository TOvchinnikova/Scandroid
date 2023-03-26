package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.data.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.GetCodeUseCase
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class GetCodeInteractor(
    private val repository: CodeRepository
) : GetCodeUseCase {

    override fun invoke(id: UUID): Flow<Code?> {
        return repository.getCodeById(id)
    }
}