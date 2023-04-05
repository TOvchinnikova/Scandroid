package com.t_ovchinnikova.android.scandroid_2.core_domain.repository

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.Flow
import java.util.*

interface CodeRepository {

    suspend fun addCode(code: Code): Boolean

    suspend fun deleteCode(codeId: UUID)

    suspend fun deleteAllCodes()

    fun getCodes(): Flow<List<Code>>

    fun getCodesWithFilter(filterText: String): Flow<List<Code>>

    fun getCodeByIdAsync(codeUuid: UUID): Flow<Code?>

    fun getCodeById(id: UUID): Code?
}