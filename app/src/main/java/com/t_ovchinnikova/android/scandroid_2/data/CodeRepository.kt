package com.t_ovchinnikova.android.scandroid_2.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import java.util.concurrent.Executors

class CodeRepository private constructor(context: Context){

    private val database : CodeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CodeDatabase::class.java,
        "scandroid"
    ).addMigrations(migration_1_2, migration_2_3)
        .build()

    private val codeDao = database.CodeDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun addCode(code: Code) {
        executor.execute {
            codeDao.addCode(code)
        }
    }

    fun deleteCode(codeId: Long) {
        Log.d("MyLog", "deleteCode")
        executor.execute {
            codeDao.deleteCode(codeId)
        }
    }

    fun updateCode(code: Code) {
        Log.d("MyLog", "$code")
        executor.execute {
            codeDao.updateCode(code)
        }
    }

    fun getCodes(): LiveData<List<Code>> = codeDao.getCodes()

    companion object {
        private var INSTANCE: CodeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CodeRepository(context)
            }
        }

        fun get(): CodeRepository {
            return INSTANCE ?:
            throw IllegalStateException("CodeRepository must be initialized")
        }
    }

}