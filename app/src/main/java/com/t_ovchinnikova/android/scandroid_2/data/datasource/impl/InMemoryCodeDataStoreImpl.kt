package com.t_ovchinnikova.android.scandroid_2.data.datasource.impl

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.data.datasource.InMemoryCodeDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryCodeDataStoreImpl : InMemoryCodeDataStore {

    private val codeFlow = MutableStateFlow<Code?>(null)

    override fun setCode(code: Code) {
        codeFlow.value = code
    }

    override fun getCode(): Flow<Code?> =
        codeFlow.asStateFlow()
}