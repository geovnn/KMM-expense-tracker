package com.giovannigatti.hackaton.ui.screen.edit_recipient

import androidx.lifecycle.ViewModel
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.repository.RecipientRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditRecipientViewModel(
    private val recipientRepository: RecipientRepository,
): ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _uiState = MutableStateFlow(EditRecipientUiState())
    val uiState: StateFlow<EditRecipientUiState> = _uiState

    fun saveRecipient(recipient: Recipient) {
        viewModelScope.launch(Dispatchers.IO) {
            recipientRepository.insert(recipient)
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