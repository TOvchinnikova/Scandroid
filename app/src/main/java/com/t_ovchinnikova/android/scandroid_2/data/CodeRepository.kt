package com.t_ovchinnikova.android.scandroid_2.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.t_ovchinnikova.android.scandroid_2.domain.Code

class CodeRepository(application: Application) {

    private val database: CodeDatabase = CodeDatabase.newInstance(application)

    private val mapper = CodeMapper()

    private val codeDao = database.CodeDao()

    suspend fun addCode(code: Code): Long {
        return codeDao.addCode(mapper.mapEntityToDbModel(code))
    }

    suspend fun deleteCode(codeId: Long) {
        codeDao.deleteCode(codeId)
    }

    suspend fun deleteAllCodes() {
        codeDao.deleteAllCodes()
    }

    fun getCodes(): LiveData<List<Code>> =
        Transformations.map(codeDao.getCodes()) {
            mapper.mapListDbModelToListEntity(it)
        }

    fun getCodesWithFilter(filterText: String = ""): LiveData<List<Code>> =
        Transformations.map(codeDao.getCodesWithFilter(filterText)) {
            mapper.mapListDbModelToListEntity(it)
        }


    companion object {
        private var INSTANCE: CodeRepository? = null

        fun initialize(application: Application) {
            if (INSTANCE == null) {
                INSTANCE = CodeRepository(application)
            }
        }

        fun get(): CodeRepository {
            return INSTANCE ?: throw IllegalStateException("CodeRepository must be initialized")
        }
    }
}