package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetCodesUseCase
import kotlinx.coroutines.flow.Flow

class GetCodesInteractor(
    private val repository: CodeRepository
) : GetCodesUseCase {

    override fun invoke(): Flow<List<Code>> {
        return repository.getCodes()
    }
}