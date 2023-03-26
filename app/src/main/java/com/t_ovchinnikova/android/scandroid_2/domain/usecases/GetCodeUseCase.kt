package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import com.t_ovchinnikova.android.scandroid_2.domain.Code
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GetCodeUseCase {

    operator fun invoke(id: UUID): Flow<Code?>
}