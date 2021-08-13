package com.t_ovchinnikova.android.scandroid_2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.t_ovchinnikova.android.scandroid_2.model.Barcode

@Dao
interface BarcodeDao {

    @Query("SELECT * FROM codes ORDER BY date DESC")
    fun getBarcodes(): List<Barcode>

    @Query("SELECT * FROM codes WHERE id = :id")
    fun getBarcode(id: Long): Barcode

    @Insert
    fun addBarcode(barcode: Barcode)

    @Query("DELETE FROM codes WHERE id = :id")
    fun delete(id: Long)


}