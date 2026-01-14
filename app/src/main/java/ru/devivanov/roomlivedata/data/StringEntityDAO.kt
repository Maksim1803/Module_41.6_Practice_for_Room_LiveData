package ru.devivanov.roomlivedata.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StringEntityDAO {
    @Query("SELECT * FROM string_table ORDER BY id DESC LIMIT 1")
    fun getLastData(): LiveData<StringEntity>

    @Query("SELECT * FROM string_table ORDER BY id DESC")
    fun getAllData(): LiveData<List<StringEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putData(stringEntity: StringEntity)
}