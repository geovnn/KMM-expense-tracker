package com.giovannigatti.hackaton.ui.screen.edit_category

import androidx.lifecycle.ViewModel
import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.repository.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditCategoryViewModel(
    private val categoryRepository: CategoryRepository,
): ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _uiState = MutableStateFlow(EditCategoryUiState())
    val uiState: StateFlow<EditCategoryUiState> = _uiState

    fun saveRecipient(cagegory: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.insert(cagegory)
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