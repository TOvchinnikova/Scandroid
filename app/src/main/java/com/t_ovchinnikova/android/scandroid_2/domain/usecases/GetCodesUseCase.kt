package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import com.t_ovchinnikova.android.scandroid_2.domain.Code
import kotlinx.coroutines.flow.Flow

interface GetCodesUseCase {

    operator fun invoke(): Flow<List<Code>>
}