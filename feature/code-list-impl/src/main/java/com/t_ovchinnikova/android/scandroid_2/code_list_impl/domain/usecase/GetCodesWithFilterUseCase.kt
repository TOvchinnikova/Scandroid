package com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.Flow

interface GetCodesWithFilterUseCase {

    operator fun invoke(filterText: String): Flow<List<Code>>
}