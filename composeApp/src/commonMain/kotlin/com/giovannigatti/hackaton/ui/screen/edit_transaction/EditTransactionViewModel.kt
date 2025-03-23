package com.giovannigatti.hackaton.ui.screen.edit_transaction

import androidx.lifecycle.ViewModel
import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.model.TransactionType
import com.giovannigatti.hackaton.repository.CategoryRepository
import com.giovannigatti.hackaton.repository.RecipientRepository
import com.giovannigatti.hackaton.repository.TransactionRepository
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

class EditTransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val recipientRepository: RecipientRepository,
    private val categoryRepository: CategoryRepository,
    ): ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _uiState = MutableStateFlow(EditTransactionUiState())
    val uiState: StateFlow<EditTransactionUiState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    recipientsList = recipientRepository.getAll(),
                    categoryList = categoryRepository.getAll()
                )
            }
        }
    }

    fun saveTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.insert(transaction)
            _uiState.update {
                it.copy(shouldClose = true)
            }
        }
    }

    fun resetOnClose() {
        _uiState.update {
            it.copy(shouldClose = false)
        }
    }
}