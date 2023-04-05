package com.t_ovchinnikova.android.scandroid_2.data.datasource.impl

import com.t_ovchinnikova.android.scandroid_2.core_db_impl.CodeDao
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel
import com.t_ovchinnikova.android.scandroid_2.data.datasource.CodeDataSource
import kotlinx.coroutines.flow.Flow
import java.util.*

class CodeDataSourceImpl(
    private val codeDao: CodeDao
) : CodeDataSource {

    override suspend fun addCode(code: CodeDbModel): Long {
        return codeDao.addCode(code)
    }

    override suspend fun deleteCode(codeId: UUID) {
        codeDao.deleteCode(codeId)
    }

    override suspend fun deleteAllCodes() {
        codeDao.deleteAllCodes()
    }

    override fun getCodes(): Flow<List<CodeDbModel>> {
        return codeDao.getCodes()
    }

    override fun getCodesWithFilter(filterText: String): Flow<List<CodeDbModel>> {
        return codeDao.getCodesWithFilter(filterText)
    }

    override fun getCodeByIdAsync(id: UUID): Flow<CodeDbModel> {
        return codeDao.getCodeByIdAsync(id)
    }

    override fun getCodeById(id: UUID): CodeDbModel? {
        return codeDao.getCodeById(id)
    }
}