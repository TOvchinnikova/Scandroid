package com.t_ovchinnikova.android.scandroid_2.data.datasource

import androidx.lifecycle.LiveData
import com.t_ovchinnikova.android.scandroid_2.data.entity.CodeDbModel
import kotlinx.coroutines.flow.Flow

interface CodeDataSource {

    suspend fun addCode(code: CodeDbModel): Long

    suspend fun deleteCode(codeId: Long)

    suspend fun deleteAllCodes()

    fun getCodes(): Flow<List<CodeDbModel>>

    fun getCodesWithFilter(filterText: String): LiveData<List<CodeDbModel>>

    fun getCodeById(id: Long): Flow<CodeDbModel?>
}