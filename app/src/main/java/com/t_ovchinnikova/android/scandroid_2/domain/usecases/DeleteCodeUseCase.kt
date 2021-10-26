package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import com.t_ovchinnikova.android.scandroid_2.domain.CodeRepository

class DeleteCodeUseCase(private val repository: CodeRepository) {

    suspend fun deleteCode(codeId: Long) {
        repository.deleteCode(codeId)
    }
}