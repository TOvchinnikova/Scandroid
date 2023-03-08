package com.t_ovchinnikova.android.scandroid_2.core_db_impl.migrations

import androidx.room.migration.Migration

object Migrations {

    val list: Array<Migration> = arrayOf(
        Migration_1_2(),
        Migration_2_3(),
        Migration_3_4()
    )
}