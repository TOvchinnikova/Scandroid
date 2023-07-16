package com.t_ovchinnikova.android.scandroid_2.code_list_impl.usecases

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.Flow

interface GetCodesUseCase {

    operator fun invoke(): Flow<List<Code>>
}