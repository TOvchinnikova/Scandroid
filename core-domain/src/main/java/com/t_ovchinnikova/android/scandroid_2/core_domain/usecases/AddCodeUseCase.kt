package com.t_ovchinnikova.android.scandroid_2.core_domain.usecases

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code

interface AddCodeUseCase {

    suspend operator fun invoke(code: Code): Boolean
}