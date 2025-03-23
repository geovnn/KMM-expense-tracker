package com.giovannigatti.hackaton.model

import kotlinx.datetime.LocalDate

data class Budget(
    val start: LocalDate,
    val end: LocalDate,
    val amount: Double,
    val transactions: List<Transaction>
)
