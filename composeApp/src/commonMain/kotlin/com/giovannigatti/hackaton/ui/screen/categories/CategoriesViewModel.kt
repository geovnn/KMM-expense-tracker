package com.giovannigatti.hackaton.ui.screen.categories

import androidx.lifecycle.ViewModel
import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.repository.CategoryRepository
import com.giovannigatti.hackaton.repository.RecipientRepository
import com.giovannigatti.hackaton.ui.screen.recipients.RecipientsUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val categoryRepository: CategoryRepository
): ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState

    fun loadRecipients() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    categories = categoryRepository.getAll()
                )
            }
        }
    }

    fun refresh() {
        loadRecipients()
    }
}