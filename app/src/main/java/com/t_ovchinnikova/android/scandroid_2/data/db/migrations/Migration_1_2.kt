package com.t_ovchinnikova.android.scandroid_2.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration_1_2 : Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE $MIGRATION_TABLE_CODES ADD COLUMN note TEXT NOT NULL DEFAULT '' "
        )
    }

    companion object {
        const val MIGRATION_TABLE_CODES = "codes"
    }
}