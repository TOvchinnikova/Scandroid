package com.t_ovchinnikova.android.scandroid_2.code_details_impl.repository

import com.t_ovchinnikova.android.scandroid_2.code_details_api.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.datasource.CodeDataSource
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.datasource.InMemoryCodeDataStore
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.util.UUID

class CodeRepositoryImpl(
    private val codeDataSource: CodeDataSource,
    private val inMemoryCodeDataStore: InMemoryCodeDataStore
) : CodeRepository {

    override suspend fun addCode(code: Code): Boolean {
        inMemoryCodeDataStore.setCode(code)
        return codeDataSource.addCode(code = code) != -1L
    }

    override suspend fun deleteCode(codeId: UUID) {
        codeDataSource.deleteCode(codeId)
    }

    override fun getCodeByIdAsync(codeUuid: UUID): Flow<Code?> {
        return flow {
            if (inMemoryCodeDataStore.getCodeById(codeUuid) != null) {
                emitAll(inMemoryCodeDataStore.getCodeAsync())
            } else {
                codeDataSource.getCodeById(codeUuid)?.let {
                    inMemoryCodeDataStore.setCode(it)
                    emit(it)
                }
            }
        }
    }

    override fun getCodeById(id: UUID): Code? {
        return getCodeById(id)
    }
}