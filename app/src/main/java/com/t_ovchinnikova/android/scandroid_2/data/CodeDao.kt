package com.t_ovchinnikova.android.scandroid_2.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.t_ovchinnikova.android.scandroid_2.domain.Code

@Dao
interface CodeDao {

    @Query("SELECT * FROM codes ORDER BY date DESC")
    fun getCodes(): LiveData<List<Code>>

    @Query("SELECT * FROM codes WHERE id = :id")
    fun getCode(id: Long): Code

    @Insert
    fun addCode(code: Code): Long

    @Update
    fun updateCode(code: Code)

    @Query("DELETE FROM codes WHERE id = :id")
    fun deleteCode(id: Long)


}