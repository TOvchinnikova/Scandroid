package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetCodesWithFilterUseCase
import kotlinx.coroutines.flow.Flow

class GetCodesWithFilterInteractor(
    private val repository: CodeRepository
) : GetCodesWithFilterUseCase {

    override fun invoke(filterText: String): Flow<List<Code>> {
        return repository.getCodesWithFilter(filterText)
    }
}