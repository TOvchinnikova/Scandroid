package com.t_ovchinnikova.android.scandroid_2.code_list_impl.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.repository.CodeListRepository
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.usecases.GetCodesWithFilterUseCase
import kotlinx.coroutines.flow.Flow

class GetCodesWithFilterInteractor(
    private val repository: CodeListRepository
) : GetCodesWithFilterUseCase {

    override fun invoke(filterText: String): Flow<List<Code>> {
        return repository.getCodesWithFilter(filterText)
    }
}