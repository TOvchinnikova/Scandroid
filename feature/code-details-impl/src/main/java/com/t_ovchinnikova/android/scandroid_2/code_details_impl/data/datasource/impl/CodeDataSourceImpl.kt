package com.t_ovchinnikova.android.scandroid_2.code_details_impl.data.datasource.impl

import com.t_ovchinnikova.android.scandroid_2.core_db_impl.CodeDao
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.data.datasource.CodeDataSource
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.mappers.CodeMapper
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class CodeDataSourceImpl(
    private val codeMapper: CodeMapper,
    private val codeDao: CodeDao
) : CodeDataSource {

    override suspend fun addCode(code: CodeEntity): Long {
        return codeDao.addCode(codeMapper.mapEntityToDbModel(code))
    }

    override suspend fun deleteCode(codeId: UUID) {
        codeDao.deleteCode(codeId)
    }

    override fun getCodeByIdAsync(id: UUID): Flow<CodeEntity?> {
        return codeDao.getCodeByIdAsync(id).map {
            it?.let { codeMapper.mapDbModelToEntity(it) }
        }
    }

    override fun getCodeById(id: UUID): CodeEntity? {
        val code = codeDao.getCodeById(id)
        return code?.let {
            codeMapper.mapDbModelToEntity(it)
        }
    }

    override fun updateFavoriteToggle(codeId: UUID, isFavorite: Boolean): Int {
        return codeDao.updateFavoriteToggle(codeId, isFavorite)
    }
}