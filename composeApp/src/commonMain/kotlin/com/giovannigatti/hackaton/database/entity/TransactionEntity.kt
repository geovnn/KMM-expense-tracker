package com.giovannigatti.hackaton.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.giovannigatti.hackaton.database.entity.RecipientEntity
import com.giovannigatti.hackaton.model.TransactionType
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(entity = CategoryEntity::class, parentColumns = ["id"], childColumns = ["categoryId"]),
        ForeignKey(entity = RecipientEntity::class, parentColumns = ["id"], childColumns = ["recipientId"])
    ],
    indices = [Index("categoryId"), Index("recipientId")]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val description: String?,
    val date: LocalDate,
    val type: TransactionType,
    val categoryId: Long?,
    val recipientId: Long?
)