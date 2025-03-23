package com.giovannigatti.hackaton.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipients")
data class RecipientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)