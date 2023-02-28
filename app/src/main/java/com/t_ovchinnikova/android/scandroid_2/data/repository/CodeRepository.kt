package com.t_ovchinnikova.android.scandroid_2.data.repository

import androidx.lifecycle.LiveData
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import kotlinx.coroutines.flow.Flow
import java.util.*

interface CodeRepository {

    suspend fun addCode(code: Code): Long

    suspend fun deleteCode(codeId: UUID)

    suspend fun deleteAllCodes()

    fun getCodes(): Flow<List<Code>>

    fun getCodesWithFilter(filterText: String): LiveData<List<Code>>

    fun getCodeById(id: Long): Flow<Code?>
}