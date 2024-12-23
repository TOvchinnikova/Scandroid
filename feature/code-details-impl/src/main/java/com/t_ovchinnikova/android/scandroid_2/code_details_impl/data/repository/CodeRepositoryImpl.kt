package com.t_ovchinnikova.android.scandroid_2.code_details_impl.data.repository

import com.t_ovchinnikova.android.scandroid_2.code_details_impl.domain.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.data.datasource.CodeDataSource
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class CodeRepositoryImpl(
    private val codeDataSource: CodeDataSource
) : CodeRepository {

    override suspend fun addCode(code: CodeEntity): Boolean {
        return codeDataSource.addCode(code = code) != -1L
    }

    override suspend fun deleteCode(codeId: UUID) {
        codeDataSource.deleteCode(codeId)
    }

    override fun getCodeByIdAsync(codeUuid: UUID): Flow<CodeEntity?> {
        return codeDataSource.getCodeByIdAsync(codeUuid)
    }

    override fun getCodeById(id: UUID): CodeEntity? {
        return codeDataSource.getCodeById(id)
    }

    override fun updateFavoriteToggle(codeId: UUID, isFavorite: Boolean): Boolean {
        return codeDataSource.updateFavoriteToggle(codeId, isFavorite) != -1
    }
}