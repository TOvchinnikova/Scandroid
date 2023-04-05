package com.t_ovchinnikova.android.scandroid_2.data.datasource

import com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel
import kotlinx.coroutines.flow.Flow
import java.util.*

interface CodeDataSource {

    suspend fun addCode(code: CodeDbModel): Long

    suspend fun deleteCode(codeId: UUID)

    suspend fun deleteAllCodes()

    fun getCodes(): Flow<List<CodeDbModel>>

    fun getCodesWithFilter(filterText: String): Flow<List<CodeDbModel>>

    fun getCodeByIdAsync(id: UUID): Flow<CodeDbModel>

    fun getCodeById(id: UUID): CodeDbModel?
}