package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import com.t_ovchinnikova.android.scandroid_2.domain.CodeRepository

class DeleteAllCodesUseCase(private val repository: CodeRepository) {

    suspend fun deleteAllCodes() {
        repository.deleteAllCodes()
    }
}