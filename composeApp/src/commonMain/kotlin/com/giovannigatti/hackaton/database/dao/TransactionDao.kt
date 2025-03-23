package com.giovannigatti.hackaton.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.giovannigatti.hackaton.data.database.entity.TransactionEntity
import com.giovannigatti.hackaton.database.entity.TransactionWithRelations


@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity): Long

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    suspend fun getAll(): List<TransactionEntity>

    @Transaction
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    suspend fun getAllWithRelations(): List<TransactionWithRelations>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getById(id: Long): TransactionEntity?

    @Transaction
    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getByIdWithRelations(id: Long): TransactionWithRelations?

    @Query("DELETE FROM transactions")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(transaction: TransactionEntity)
}