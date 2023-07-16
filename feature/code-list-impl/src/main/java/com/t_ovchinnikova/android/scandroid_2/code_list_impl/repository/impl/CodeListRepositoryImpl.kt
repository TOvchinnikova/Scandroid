package com.t_ovchinnikova.android.scandroid_2.code_list_impl.repository.impl

import com.t_ovchinnikova.android.scandroid_2.code_list_impl.datasource.CodeListDataSource
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.repository.CodeListRepository
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.mappers.CodeMapper
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CodeListRepositoryImpl(
    private val codeDataSource: CodeListDataSource,
    private val codeMapper: CodeMapper
) : CodeListRepository {

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
}