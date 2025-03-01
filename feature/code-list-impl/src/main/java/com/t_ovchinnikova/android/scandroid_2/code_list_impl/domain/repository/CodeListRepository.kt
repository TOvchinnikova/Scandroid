package com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.repository

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity
import kotlinx.coroutines.flow.Flow

interface CodeListRepository {

    suspend fun deleteAllCodes()

    fun getCodes(): Flow<List<CodeEntity>>

    fun getCodesWithFilter(filterText: String): Flow<List<CodeEntity>>
}