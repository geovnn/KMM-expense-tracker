package com.giovannigatti.hackaton.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giovannigatti.hackaton.database.entity.BudgetEntity

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: BudgetEntity): Long

    @Query("SELECT * FROM budgets ORDER BY start DESC")
    suspend fun getAll(): List<BudgetEntity>

    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getById(id: Long): BudgetEntity?

    @Delete
    suspend fun delete(budget: BudgetEntity)

    @Query("DELETE FROM budgets")
    suspend fun deleteAll()
}