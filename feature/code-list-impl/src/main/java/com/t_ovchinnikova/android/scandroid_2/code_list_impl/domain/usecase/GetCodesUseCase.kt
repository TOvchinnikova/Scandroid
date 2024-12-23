package com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity
import kotlinx.coroutines.flow.Flow

interface GetCodesUseCase {

    operator fun invoke(): Flow<List<CodeEntity>>
}