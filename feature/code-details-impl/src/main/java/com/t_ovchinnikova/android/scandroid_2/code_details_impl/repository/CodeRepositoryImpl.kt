package com.t_ovchinnikova.android.scandroid_2.code_details_impl.repository

import com.t_ovchinnikova.android.scandroid_2.code_details_api.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.data.datasource.CodeDataSource
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class CodeRepositoryImpl(
    private val codeDataSource: CodeDataSource
) : CodeRepository {

    override suspend fun addCode(code: Code): Boolean {
        return codeDataSource.addCode(code = code) != -1L
    }

    override suspend fun deleteCode(codeId: UUID) {
        codeDataSource.deleteCode(codeId)
    }

    override fun getCodeByIdAsync(codeUuid: UUID): Flow<Code?> {
        return codeDataSource.getCodeByIdAsync(codeUuid)
    }

    override fun getCodeById(id: UUID): Code? {
        return getCodeById(id)
    }
}