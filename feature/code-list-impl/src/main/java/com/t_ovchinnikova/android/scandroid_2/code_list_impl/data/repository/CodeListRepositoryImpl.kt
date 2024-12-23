package com.t_ovchinnikova.android.scandroid_2.code_list_impl.data.repository

import com.t_ovchinnikova.android.scandroid_2.code_list_impl.data.datasource.CodeListDataSource
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.repository.CodeListRepository
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.mappers.CodeMapper
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CodeListRepositoryImpl(
    private val codeDataSource: CodeListDataSource,
    private val codeMapper: CodeMapper
) : CodeListRepository {

    override suspend fun deleteAllCodes() {
        codeDataSource.deleteAllCodes()
    }

    override fun getCodes(): Flow<List<CodeEntity>> =
        codeDataSource.getCodes().map {
            codeMapper.mapListDbModelToListEntity(it)
        }

    override fun getCodesWithFilter(filterText: String): Flow<List<CodeEntity>> =
        codeDataSource.getCodesWithFilter(filterText).map {
            codeMapper.mapListDbModelToListEntity(it)
        }
}