package com.giovannigatti.hackaton.ui.screen.charts

import com.giovannigatti.hackaton.model.Transaction

data class ChartsUiState(
    val transactions: List<Transaction> = emptyList()
)
