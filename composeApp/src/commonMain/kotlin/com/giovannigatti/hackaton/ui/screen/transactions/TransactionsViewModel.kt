package com.giovannigatti.hackaton.ui.screen.transactions

import androidx.lifecycle.ViewModel
import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.model.TransactionType
import com.giovannigatti.hackaton.repository.CategoryRepository
import com.giovannigatti.hackaton.repository.RecipientRepository
import com.giovannigatti.hackaton.repository.TransactionRepository
import com.giovannigatti.hackaton.ui.screen.edit_transaction.EditTransactionUiState
import com.giovannigatti.hackaton.ui.screen.home.HomeScreenUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class TransactionsViewModel(
    private val transactionRepository: TransactionRepository
): ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _uiState = MutableStateFlow(TransactionsScreenUiState())
    val uiState: StateFlow<TransactionsScreenUiState> = _uiState

    fun loadTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    recentTransactions = transactionRepository.getAll()
                )
            }
        }
    }

    fun refresh() {
        loadTransactions()
    }
}