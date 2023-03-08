package com.t_ovchinnikova.android.scandroid_2.core_db_impl.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration_2_3 : Migration(2, 3) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE $MIGRATION_TABLE_CODES ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0 "
        )
    }

    companion object {
        const val MIGRATION_TABLE_CODES = "codes"
    }
}