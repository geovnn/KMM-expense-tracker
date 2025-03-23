package com.giovannigatti.hackaton.ui.screen.transactions

import com.giovannigatti.hackaton.model.Transaction

data class TransactionsScreenUiState(
    val recentTransactions: List<Transaction> = emptyList()
)
