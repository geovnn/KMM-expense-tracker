package com.giovannigatti.hackaton.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate


@Entity(tableName = "budgets")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val start: LocalDate,
    val end: LocalDate,
    val amount: Double
)