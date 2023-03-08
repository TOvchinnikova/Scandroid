package com.t_ovchinnikova.android.scandroid_2.data.datasource.impl

import androidx.lifecycle.LiveData
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.CodeDao
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel
import com.t_ovchinnikova.android.scandroid_2.data.datasource.CodeDataSource
import kotlinx.coroutines.flow.Flow
import java.util.*

class CodeDataSourceImpl(
    private val codeDao: com.t_ovchinnikova.android.scandroid_2.core_db_impl.CodeDao
) : CodeDataSource {

    override suspend fun addCode(code: com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel): Long {
        return codeDao.addCode(code)
    }

    override suspend fun deleteCode(codeId: UUID) {
        codeDao.deleteCode(codeId)
    }

    override suspend fun deleteAllCodes() {
        codeDao.deleteAllCodes()
    }

    override fun getCodes(): Flow<List<com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel>> {
        return codeDao.getCodes()
    }

    override fun getCodesWithFilter(filterText: String): LiveData<List<com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel>> {
        return codeDao.getCodesWithFilter(filterText)
    }

    override fun getCodeById(id: Long): Flow<com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel?> {
        return codeDao.getCodeById(id)
    }
}