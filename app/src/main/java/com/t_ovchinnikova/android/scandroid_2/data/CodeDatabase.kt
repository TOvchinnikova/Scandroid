package com.t_ovchinnikova.android.scandroid_2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.t_ovchinnikova.android.scandroid_2.domain.Code

@Database(entities = [Code::class], version = 3)
@TypeConverters(CodeDatabaseTypeConverter::class)
abstract class CodeDatabase : RoomDatabase() {

    abstract fun CodeDao(): CodeDao
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
            "ALTER TABLE codes ADD COLUMN isFavorite INTEGER NOT NULL CHECK (isFavorite in (0, 1)) DEFAULT 0 "
        )
    }
}