package com.t_ovchinnikova.android.scandroid_2.core_domain.usecases

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

interface GetCodeUseCase {

    fun invokeAsync(id: UUID): Flow<CodeEntity?>
}