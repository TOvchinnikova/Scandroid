package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.CodeRepository

class AddCodeUseCase(private val repository: CodeRepository) {

    suspend fun addCode(code: Code): Long {
        return repository.addCode(code)
    }
}