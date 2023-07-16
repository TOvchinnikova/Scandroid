package com.t_ovchinnikova.android.scandroid_2.code_details_api.repository

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.Flow
import java.util.*

interface CodeRepository {

    suspend fun addCode(code: Code): Boolean

    suspend fun deleteCode(codeId: UUID)

    fun getCodeByIdAsync(codeUuid: UUID): Flow<Code?>

    fun getCodeById(id: UUID): Code?
}