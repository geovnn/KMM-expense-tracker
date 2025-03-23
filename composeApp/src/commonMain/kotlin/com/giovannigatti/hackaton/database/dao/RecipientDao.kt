package com.giovannigatti.hackaton.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giovannigatti.hackaton.database.entity.RecipientEntity

@Dao
interface RecipientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipient: RecipientEntity): Long

    @Query("SELECT * FROM recipients ORDER BY name ASC")
    suspend fun getAll(): List<RecipientEntity>

    @Query("SELECT * FROM recipients WHERE id = :id")
    suspend fun getById(id: Long): RecipientEntity?

    @Delete
    suspend fun delete(recipient: RecipientEntity)

    @Query("DELETE FROM recipients")
    suspend fun deleteAll()
}