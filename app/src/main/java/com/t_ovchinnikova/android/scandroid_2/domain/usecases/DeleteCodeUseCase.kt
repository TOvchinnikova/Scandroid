package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import java.util.UUID

interface DeleteCodeUseCase {

    suspend operator fun invoke(codeId: UUID)
}