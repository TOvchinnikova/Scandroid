package com.t_ovchinnikova.android.scandroid_2.code_list_impl.repository

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.Flow

interface CodeListRepository {

    suspend fun deleteAllCodes()

    fun getCodes(): Flow<List<Code>>

    fun getCodesWithFilter(filterText: String): Flow<List<Code>>
}