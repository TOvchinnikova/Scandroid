package com.t_ovchinnikova.android.scandroid_2.code_details_impl.datasource

import com.t_ovchinnikova.android.scandroid_2.core_db_impl.CodeDao
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.datasource.CodeDataSource
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel
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

    override fun getCodeByIdAsync(id: UUID): Flow<CodeDbModel> {
        return codeDao.getCodeByIdAsync(id)
    }

    override fun getCodeById(id: UUID): CodeDbModel? {
        return codeDao.getCodeById(id)
    }
}