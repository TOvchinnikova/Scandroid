package com.t_ovchinnikova.android.scandroid_2.code_list_impl.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.repository.CodeListRepository
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.usecases.GetCodesUseCase
import kotlinx.coroutines.flow.Flow

class GetCodesInteractor(
    private val repository: CodeListRepository
) : GetCodesUseCase {

    override fun invoke(): Flow<List<Code>> {
        return repository.getCodes()
    }
}