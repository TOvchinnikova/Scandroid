package com.t_ovchinnikova.android.scandroid_2.data.datasource

import androidx.lifecycle.LiveData
import com.t_ovchinnikova.android.scandroid_2.data.entity.CodeDbModel

interface CodeDataSource {

    suspend fun addCode(code: CodeDbModel): Long

    suspend fun deleteCode(codeId: Long)

    suspend fun deleteAllCodes()

    fun getCodes(): LiveData<List<CodeDbModel>>

    fun getCodesWithFilter(filterText: String): LiveData<List<CodeDbModel>>


}