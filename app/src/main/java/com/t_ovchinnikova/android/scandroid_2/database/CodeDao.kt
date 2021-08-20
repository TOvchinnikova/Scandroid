package com.t_ovchinnikova.android.scandroid_2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.t_ovchinnikova.android.scandroid_2.model.Code

@Dao
interface CodeDao {

    @Query("SELECT * FROM codes ORDER BY date DESC")
    fun getCodes(): LiveData<List<Code>>

    @Query("SELECT * FROM codes WHERE id = :id")
    fun getCode(id: Long): Code

    @Insert
    fun addCode(code: Code)

    @Query("DELETE FROM codes WHERE id = :id")
    fun delete(id: Long)


}