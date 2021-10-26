package com.t_ovchinnikova.android.scandroid_2.domain

import androidx.lifecycle.LiveData

interface CodeRepository {

    suspend fun addCode(code: Code): Long

    suspend fun deleteCode(codeId: Long)

    suspend fun deleteAllCodes()

    fun getCodes(): LiveData<List<Code>>

    fun getCodesWithFilter(filterText: String): LiveData<List<Code>>
}