package com.example.appyhour.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BarDatabaseDao {

    @Insert
    suspend fun insert(bottle: Bottle)
    @Update
    suspend fun update(bottle: Bottle)
    @Query("SELECT * from bar_table WHERE bottleId = :key")
    suspend fun get(key: Long): Bottle?

    @Query("DELETE FROM bar_table")
    suspend fun clear()

    @Query("SELECT * FROM bar_table ORDER BY bottleId DESC")
    fun getBottles(): LiveData<List<Bottle>>

    @Query("SELECT * FROM bar_table ORDER BY bottleId DESC LIMIT 1")
    suspend fun getFirstBottle(): Bottle?

    @Query("SELECT * from bar_table WHERE bottleId = :key")
    fun getBottleWithId(key: Long): LiveData<Bottle>
}
