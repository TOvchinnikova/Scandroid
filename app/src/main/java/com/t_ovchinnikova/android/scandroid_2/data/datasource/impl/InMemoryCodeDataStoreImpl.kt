package com.t_ovchinnikova.android.scandroid_2.data.datasource.impl

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.data.datasource.InMemoryCodeDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import java.util.*

class InMemoryCodeDataStoreImpl : InMemoryCodeDataStore {

    private val codeFlow = MutableStateFlow<Code?>(null)

    override fun setCode(code: Code) {
        codeFlow.value = code
    }

    override fun getCodeAsync(): Flow<Code?> {
        return codeFlow
    }

    override suspend fun getCodeById(codeUuid: UUID): Code? {
        val code = codeFlow.firstOrNull()
        return if (code?.id == codeUuid) code else null
    }
}