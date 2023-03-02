package com.t_ovchinnikova.android.scandroid_2.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

class Migration_3_4 : Migration(3, 4) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS $MIGRATION_TABLE_CODES_OLD;")
        database.execSQL("ALTER TABLE $MIGRATION_TABLE_CODES RENAME TO $MIGRATION_TABLE_CODES_OLD ")
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS $MIGRATION_TABLE_CODES " +
                    "(id TEXT NOT NULL PRIMARY KEY, text TEXT NOT NULL, format INTEGER NOT NULL, " +
                    "type INTEGER NOT NULL, date INTEGER NOT NULL, note TEXT NOT NULL, " +
                    "isFavorite INTEGER NOT NULL)"
        )
        database.execSQL("INSERT INTO $MIGRATION_TABLE_CODES " +
                "SELECT ${UUID.randomUUID()}, text, format, type, date, note, isFavorite " +
                "FROM $MIGRATION_TABLE_CODES_OLD")
        database.execSQL("DROP TABLE IF EXISTS $MIGRATION_TABLE_CODES_OLD")
    }

    companion object {
        const val MIGRATION_TABLE_CODES_OLD = "codes_old"
        const val MIGRATION_TABLE_CODES = "codes"
    }
}