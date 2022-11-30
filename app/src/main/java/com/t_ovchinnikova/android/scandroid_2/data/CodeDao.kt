package com.t_ovchinnikova.android.scandroid_2.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.t_ovchinnikova.android.scandroid_2.data.entity.CodeDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CodeDao {

    @Query("SELECT * FROM codes ORDER BY date DESC")
    fun getCodes(): Flow<List<CodeDbModel>>

    @Query("SELECT * FROM codes WHERE id = :id")
    fun getCodeById(id: Long): Flow<CodeDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCode(code: CodeDbModel): Long

    @Query("DELETE FROM codes WHERE id = :id")
    suspend fun deleteCode(id: Long)

    @Query("DELETE FROM codes")
    suspend fun deleteAllCodes()

    @Query("SELECT * FROM codes WHERE text LIKE '%' || :filterText || '%' ORDER BY date DESC")
    fun getCodesWithFilter(filterText: String): LiveData<List<CodeDbModel>>

}