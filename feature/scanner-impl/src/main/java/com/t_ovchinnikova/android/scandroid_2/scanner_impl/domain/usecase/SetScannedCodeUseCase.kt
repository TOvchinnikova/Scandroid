package com.t_ovchinnikova.android.scandroid_2.scanner_impl.domain.usecase

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity

interface SetScannedCodeUseCase {

    operator fun invoke(code: CodeEntity)
}