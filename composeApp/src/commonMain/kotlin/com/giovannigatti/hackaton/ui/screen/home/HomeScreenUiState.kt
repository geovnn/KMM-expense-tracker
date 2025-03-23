package com.giovannigatti.hackaton.ui.screen.home

import com.giovannigatti.hackaton.model.Transaction

data class HomeScreenUiState(
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val totalDifference: Double = 0.0,
    val recentTransactions: List<Transaction> = emptyList()
)
