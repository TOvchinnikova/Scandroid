package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteAllCodesUseCase

class DeleteAllCodesInteractor(
    private val repository: com.t_ovchinnikova.android.scandroid_2.core_domain.repository.CodeRepository
) : com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteAllCodesUseCase {

    override suspend fun invoke() {
        repository.deleteAllCodes()
    }
}