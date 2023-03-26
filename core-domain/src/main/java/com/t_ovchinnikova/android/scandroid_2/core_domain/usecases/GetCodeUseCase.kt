package com.t_ovchinnikova.android.scandroid_2.core_domain.usecases

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.Flow
import java.util.*

interface GetCodeUseCase {

    operator fun invoke(id: UUID): Flow<Code>
}