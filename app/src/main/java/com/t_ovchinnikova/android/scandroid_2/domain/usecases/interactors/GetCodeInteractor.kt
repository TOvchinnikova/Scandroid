package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetCodeUseCase
import kotlinx.coroutines.flow.Flow
import java.util.*

class GetCodeInteractor(
    private val repository: CodeRepository
) : GetCodeUseCase {

    override fun invokeAsync(id: UUID): Flow<Code?> {
        return repository.getCodeByIdAsync(id)
    }
}