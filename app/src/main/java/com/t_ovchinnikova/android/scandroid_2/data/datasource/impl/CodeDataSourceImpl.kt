package com.t_ovchinnikova.android.scandroid_2.data.datasource.impl

import androidx.lifecycle.LiveData
import com.t_ovchinnikova.android.scandroid_2.data.CodeDao
import com.t_ovchinnikova.android.scandroid_2.data.entity.CodeDbModel
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

    override fun getCodesWithFilter(filterText: String): LiveData<List<CodeDbModel>> {
        return codeDao.getCodesWithFilter(filterText)
    }

    override fun getCodeById(id: Long): Flow<CodeDbModel?> {
        return codeDao.getCodeById(id)
    }
}