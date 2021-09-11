package com.t_ovchinnikova.android.scandroid_2.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.t_ovchinnikova.android.scandroid_2.domain.Code

@Dao
interface CodeDao {

    @Query("SELECT * FROM codes ORDER BY date DESC")
    fun getCodes(): LiveData<List<CodeDbModel>>

    @Query("SELECT * FROM codes WHERE id = :id")
    suspend fun getCode(id: Long): CodeDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCode(code: CodeDbModel): Long

    @Query("DELETE FROM codes WHERE id = :id")
    suspend fun deleteCode(id: Long)

}