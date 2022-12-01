package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import com.t_ovchinnikova.android.scandroid_2.domain.Code
import kotlinx.coroutines.flow.Flow

interface GetCodeUseCase {

    operator fun invoke(id: Long): Flow<Code?>
}