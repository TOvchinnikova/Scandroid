package com.t_ovchinnikova.android.scandroid_2.code_list_impl.datasource

import com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel
import kotlinx.coroutines.flow.Flow

interface CodeListDataSource {

    suspend fun deleteAllCodes()

    fun getCodes(): Flow<List<CodeDbModel>>

    fun getCodesWithFilter(filterText: String): Flow<List<CodeDbModel>>
}