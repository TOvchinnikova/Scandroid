package com.t_ovchinnikova.android.scandroid_2.code_list_impl.datasource.impl

import com.t_ovchinnikova.android.scandroid_2.code_list_impl.datasource.CodeListDataSource
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.CodeDao
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel
import kotlinx.coroutines.flow.Flow

class CodeListDataSourceImpl(
    private val codeDao: CodeDao
) : CodeListDataSource {

    override suspend fun deleteAllCodes() {
        codeDao.deleteAllCodes()
    }

    override fun getCodes(): Flow<List<CodeDbModel>> {
        return codeDao.getCodes()
    }

    override fun getCodesWithFilter(filterText: String): Flow<List<CodeDbModel>> {
        return codeDao.getCodesWithFilter(filterText)
    }
}