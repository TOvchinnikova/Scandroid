package com.t_ovchinnikova.android.scandroid_2.scanner_impl.domain.usecase

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity
import kotlinx.coroutines.flow.SharedFlow

interface GetScannedCodeUseCase {

    operator fun invoke(): SharedFlow<CodeEntity>
}