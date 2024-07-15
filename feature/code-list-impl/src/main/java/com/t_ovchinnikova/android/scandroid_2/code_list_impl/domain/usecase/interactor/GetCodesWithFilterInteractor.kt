package com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase.interactor

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.repository.CodeListRepository
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase.GetCodesWithFilterUseCase
import kotlinx.coroutines.flow.Flow

class GetCodesWithFilterInteractor(
    private val repository: CodeListRepository
) : GetCodesWithFilterUseCase {

    override fun invoke(filterText: String): Flow<List<Code>> {
        return repository.getCodesWithFilter(filterText)
    }
}