package com.t_ovchinnikova.android.scandroid_2.code_details_impl.data.datasource

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface CodeDataSource {

    suspend fun addCode(code: Code): Long

    suspend fun deleteCode(codeId: UUID)

    fun getCodeByIdAsync(id: UUID): Flow<Code?>

    fun getCodeById(id: UUID): Code?
}