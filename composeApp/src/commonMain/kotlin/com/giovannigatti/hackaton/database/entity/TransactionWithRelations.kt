package com.giovannigatti.hackaton.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.giovannigatti.hackaton.data.database.entity.CategoryEntity
import com.giovannigatti.hackaton.data.database.entity.TransactionEntity

data class TransactionWithRelations(
    @Embedded val transaction: TransactionEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity?,

    @Relation(
        parentColumn = "recipientId",
        entityColumn = "id"
    )
    val recipient: RecipientEntity?
)