package com.giovannigatti.hackaton.ui.screen.home

import androidx.lifecycle.ViewModel
import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.model.TransactionType
import com.giovannigatti.hackaton.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(private val transactionRepository: TransactionRepository): ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    fun loadTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            val transactions = transactionRepository.getAll()
            val income = transactions
                .filter {it.date.month== Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).month && it.type == TransactionType.INCOME }
                .sumOf { it.amount }
            val expense = transactions
                .filter {it.date.month== Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).month && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            val difference = income-expense

            _uiState.update {
                it.copy(
                    recentTransactions = transactionRepository.getAll(),
                    totalIncome = income,
                    totalExpense = expense,
                    totalDifference = difference
                )
            }
        }
    }

    fun refresh() {
        loadTransactions()
    }
}