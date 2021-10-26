package com.t_ovchinnikova.android.scandroid_2.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.CodeRepository

class CodeRepositoryImpl(private val mapper: CodeMapper, private val codeDao: CodeDao): CodeRepository {

    /*private val mapper = CodeMapper()

    private val codeDao = database.CodeDao()*/

    override suspend fun addCode(code: Code): Long {
        return codeDao.addCode(mapper.mapEntityToDbModel(code))
    }

    override suspend fun deleteCode(codeId: Long) {
        codeDao.deleteCode(codeId)
    }

    override suspend fun deleteAllCodes() {
        codeDao.deleteAllCodes()
    }

    override fun getCodes(): LiveData<List<Code>> =
        Transformations.map(codeDao.getCodes()) {
            mapper.mapListDbModelToListEntity(it)
        }

    override fun getCodesWithFilter(filterText: String): LiveData<List<Code>> =
        Transformations.map(codeDao.getCodesWithFilter(filterText)) {
            mapper.mapListDbModelToListEntity(it)
        }


    companion object {
        private var INSTANCE: CodeRepositoryImpl? = null

        fun initialize(mapper: CodeMapper, codeDao: CodeDao): CodeRepositoryImpl {
            if (INSTANCE == null) {
                INSTANCE = CodeRepositoryImpl(mapper, codeDao)
            }
            return INSTANCE ?: throw IllegalStateException("CodeRepository must be initialized")
        }

        fun get(): CodeRepositoryImpl {
            return INSTANCE ?: throw IllegalStateException("CodeRepository must be initialized")
        }
    }
}