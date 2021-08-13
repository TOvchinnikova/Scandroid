package com.t_ovchinnikova.android.scandroid_2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.t_ovchinnikova.android.scandroid_2.model.Code

@Database(entities = [Code::class], version = 1)
@TypeConverters(CodeDatabaseTypeConverter::class)
abstract class CodeDatabase : RoomDatabase() {

    abstract fun CodeDao(): CodeDao
}