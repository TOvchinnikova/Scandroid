package com.t_ovchinnikova.android.scandroid_2.data.datasource

import androidx.lifecycle.LiveData
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface CodeDataSource {

    suspend fun addCode(code: CodeDbModel): Long

    suspend fun deleteCode(codeId: UUID)

    suspend fun deleteAllCodes()

    fun getCodes(): Flow<List<CodeDbModel>>

    fun getCodesWithFilter(filterText: String): LiveData<List<CodeDbModel>>

    fun getCodeById(id: UUID): Flow<CodeDbModel?>
}