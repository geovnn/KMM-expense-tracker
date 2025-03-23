package com.giovannigatti.hackaton.model

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate

data class Transaction(
    val id: Long,
    val amount: Double,
    val description: String?,
    val date: LocalDate,
    val category: Category?,
    val type: TransactionType,
    val recipient : Recipient?,
)
