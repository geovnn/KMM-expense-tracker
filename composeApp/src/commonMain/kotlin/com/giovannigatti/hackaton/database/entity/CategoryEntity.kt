package com.giovannigatti.hackaton.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giovannigatti.hackaton.model.TransactionType

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val color: Long // ARGB packed into Long
)