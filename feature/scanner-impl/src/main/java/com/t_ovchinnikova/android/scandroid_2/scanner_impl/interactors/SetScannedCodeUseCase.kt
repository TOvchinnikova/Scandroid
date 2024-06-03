package com.t_ovchinnikova.android.scandroid_2.scanner_impl.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code

interface SetScannedCodeUseCase {

    operator fun invoke(code: Code)
}