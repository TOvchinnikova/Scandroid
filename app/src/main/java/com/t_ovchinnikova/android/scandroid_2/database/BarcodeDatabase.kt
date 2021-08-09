package com.t_ovchinnikova.android.scandroid_2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.t_ovchinnikova.android.scandroid_2.model.Barcode

@Database(entities = [Barcode::class], version = 1)
@TypeConverters(BarcodeDatabaseTypeConverter::class)
abstract class BarcodeDatabase : RoomDatabase() {

    abstract fun barcodeDao(): BarcodeDao
}