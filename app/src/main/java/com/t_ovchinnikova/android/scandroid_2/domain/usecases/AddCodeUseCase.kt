package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import com.t_ovchinnikova.android.scandroid_2.domain.Code

interface AddCodeUseCase {

    suspend operator fun invoke(code: Code): Long
}