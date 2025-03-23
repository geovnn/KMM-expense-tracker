package com.giovannigatti.hackaton.database

import com.giovannigatti.hackaton.data.database.entity.CategoryEntity
import com.giovannigatti.hackaton.data.database.entity.TransactionEntity
import com.giovannigatti.hackaton.database.entity.BudgetEntity
import com.giovannigatti.hackaton.database.entity.RecipientEntity
import com.giovannigatti.hackaton.model.Budget
import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction

fun Category.toEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    color = color?.value?.toLong() ?: 0L
)

fun CategoryEntity.toModel(): Category = Category(
    id = id,
    name = name,
    color = androidx.compose.ui.graphics.Color(color.toULong())
)

fun Recipient.toEntity(): RecipientEntity = RecipientEntity(
    id = id,
    name = name
)

fun RecipientEntity.toModel(): Recipient = Recipient(
    id = id,
    name = name
)

fun Budget.toEntity(): BudgetEntity = BudgetEntity(
    start = start,
    end = end,
    amount = amount
)

fun BudgetEntity.toModel(transactions: List<Transaction> = emptyList()): Budget = Budget(
    start = start,
    end = end,
    amount = amount,
    transactions = transactions
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    amount = amount,
    description = description,
    date = date,
    type = type,
    categoryId = category?.id,
    recipientId = recipient?.id
)

fun TransactionEntity.toModel(
    category: Category?,
    recipient: Recipient?
): Transaction = Transaction(
    id = id,
    amount = amount,
    description = description,
    date = date,
    category = category,
    type = type,
    recipient = recipient
)