package com.t_ovchinnikova.android.scandroid_2.data.datasource

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.Flow

interface InMemoryCodeDataStore {

    fun setCode(code: Code)

    fun getCode(): Flow<Code?>
}