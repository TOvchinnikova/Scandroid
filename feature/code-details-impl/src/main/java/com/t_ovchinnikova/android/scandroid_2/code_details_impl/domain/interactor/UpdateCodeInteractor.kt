package com.t_ovchinnikova.android.scandroid_2.code_details_impl.domain.interactor

import com.t_ovchinnikova.android.scandroid_2.code_details_impl.domain.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.UpdateCodeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID

class UpdateCodeInteractor(
    private val repository: CodeRepository,
    private val dispatcher: CoroutineDispatcher
) : UpdateCodeUseCase {

    override suspend fun invoke(codeId: UUID, isFavorite: Boolean) {
        withContext(dispatcher) {
            repository.updateFavoriteToggle(codeId, isFavorite)
        }
    }
}