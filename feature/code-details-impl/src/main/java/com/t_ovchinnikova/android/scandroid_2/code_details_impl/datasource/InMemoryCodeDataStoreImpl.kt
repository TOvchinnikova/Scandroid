package com.t_ovchinnikova.android.scandroid_2.code_details_impl.datasource

import com.t_ovchinnikova.android.scandroid_2.code_details_api.datasource.InMemoryCodeDataStore
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID

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