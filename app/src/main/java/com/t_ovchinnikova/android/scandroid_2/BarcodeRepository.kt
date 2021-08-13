package com.t_ovchinnikova.android.scandroid_2

import android.content.Context
import androidx.room.Room
import com.t_ovchinnikova.android.scandroid_2.database.BarcodeDatabase
import java.util.concurrent.Executors

class BarcodeRepository private constructor(context: Context){

    private val database : BarcodeDatabase = Room.databaseBuilder(
        context.applicationContext,
        BarcodeDatabase::class.java,
        "scandroid"
    ).build()

    private val barcodeDao = database.barcodeDao()
    private val executor =Executors.newSingleThreadExecutor()

    companion object {
        private var INSTANCE: BarcodeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = BarcodeRepository(context)
            }
        }

        fun get(): BarcodeRepository {
            return INSTANCE ?:
            throw IllegalStateException("BarcodeRepository must be initialized")
        }
    }

}