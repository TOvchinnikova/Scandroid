package com.t_ovchinnikova.android.scandroid_2.code_details_api.datasource

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.Flow
import java.util.*

interface InMemoryCodeDataStore {

    fun setCode(code: Code)

    fun getCodeAsync(): Flow<Code?>

    suspend fun getCodeById(codeUuid: UUID): Code?
}