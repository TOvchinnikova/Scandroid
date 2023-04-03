package com.t_ovchinnikova.android.scandroid_2.data.repository.impl

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.data.CodeMapper
import com.t_ovchinnikova.android.scandroid_2.data.datasource.CodeDataSource
import com.t_ovchinnikova.android.scandroid_2.data.datasource.InMemoryCodeDataStore
import kotlinx.coroutines.flow.*
import java.util.*

class CodeRepositoryImpl(
    private val codeMapper: CodeMapper,
    private val codeDataSource: CodeDataSource,
    private val inMemoryCodeDataStore: InMemoryCodeDataStore
) : CodeRepository {

    override suspend fun addCode(code: Code): Long {
        return codeDataSource.addCode(codeMapper.mapEntityToDbModel(code))
    }

    override suspend fun deleteCode(codeId: UUID) {
        codeDataSource.deleteCode(codeId)
    }

    override suspend fun deleteAllCodes() {
        codeDataSource.deleteAllCodes()
    }

    override fun getCodes(): Flow<List<Code>> =
        codeDataSource.getCodes().map {
            codeMapper.mapListDbModelToListEntity(it)
        }

    override fun getCodesWithFilter(filterText: String): Flow<List<Code>> =
        codeDataSource.getCodesWithFilter(filterText).map {
            codeMapper.mapListDbModelToListEntity(it)
        }

    override fun getCodeById(id: UUID): Flow<Code> {
        return codeDataSource.getCodeById(id).map {
            codeMapper.mapDbModelToEntity(it)
        }
    }
}