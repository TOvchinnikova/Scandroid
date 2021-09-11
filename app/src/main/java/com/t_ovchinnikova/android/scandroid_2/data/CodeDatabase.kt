package com.t_ovchinnikova.android.scandroid_2.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.t_ovchinnikova.android.scandroid_2.domain.Code

@Database(entities = [CodeDbModel::class], version = 3)
@TypeConverters(CodeDatabaseTypeConverter::class)
abstract class CodeDatabase : RoomDatabase() {

    abstract fun CodeDao(): CodeDao

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
                ).addMigrations(migration_1_2, migration_2_3)
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE codes ADD COLUMN note TEXT NOT NULL DEFAULT '' "
        )
    }
}

val migration_2_3 = object : Migration(2, 3) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE codes ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0 "
        )
    }
}