package com.t_ovchinnikova.android.scandroid_2.core_db_impl

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

const val VERSION = 4

@Database(
    entities = [com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel::class],
    version = VERSION
)
@TypeConverters(CodeDatabaseTypeConverter::class)
abstract class CodeDatabase : RoomDatabase() {

    abstract fun codeDao(): CodeDao

    companion object {
        private var INSTANCE: CodeDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "scandroid"

        fun newInstance(application: Application): CodeDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    CodeDatabase::class.java,
                    DB_NAME
                ).addMigrations(*com.t_ovchinnikova.android.scandroid_2.core_db_impl.migrations.Migrations.list)
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}