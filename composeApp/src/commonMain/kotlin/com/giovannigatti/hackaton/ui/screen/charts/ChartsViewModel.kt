package com.giovannigatti.hackaton.ui.screen.charts

import androidx.lifecycle.ViewModel
import com.giovannigatti.hackaton.repository.TransactionRepository
import com.giovannigatti.hackaton.ui.screen.transactions.TransactionsScreenUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChartsViewModel(
    private val transactionRepository: TransactionRepository
): ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _uiState = MutableStateFlow(ChartsUiState())
    val uiState: StateFlow<ChartsUiState> = _uiState

    fun loadTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    transactions = transactionRepository.getAll()
                )
            }
        }
    }

    fun refresh() {
        loadTransactions()
    }
}