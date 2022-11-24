package com.t_ovchinnikova.android.scandroid_2.domain.usecases

interface DeleteCodeUseCase {

    suspend operator fun invoke(codeId: Long)
}