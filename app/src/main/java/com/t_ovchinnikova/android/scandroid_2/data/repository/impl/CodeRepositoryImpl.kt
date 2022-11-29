package com.t_ovchinnikova.android.scandroid_2.data.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.t_ovchinnikova.android.scandroid_2.data.CodeMapper
import com.t_ovchinnikova.android.scandroid_2.data.datasource.CodeDataSource
import com.t_ovchinnikova.android.scandroid_2.data.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CodeRepositoryImpl(
    private val codeMapper: CodeMapper,
    private val codeDataSource: CodeDataSource
) : CodeRepository {

    override suspend fun addCode(code: Code): Long {
        return codeDataSource.addCode(codeMapper.mapEntityToDbModel(code))
    }

    override suspend fun deleteCode(codeId: Long) {
        codeDataSource.deleteCode(codeId)
    }

    override suspend fun deleteAllCodes() {
        codeDataSource.deleteAllCodes()
    }

    override fun getCodes(): LiveData<List<Code>> =
        Transformations.map(codeDataSource.getCodes()) {
            codeMapper.mapListDbModelToListEntity(it)
        }

    override fun getCodesWithFilter(filterText: String): LiveData<List<Code>> =
        Transformations.map(codeDataSource.getCodesWithFilter(filterText)) {
            codeMapper.mapListDbModelToListEntity(it)
        }

    override fun getCodeById(id: Long): Flow<Code> {
        return codeDataSource.getCodeById(id).map {
            codeMapper.mapDbModelToEntity(it)
        }
    }
}