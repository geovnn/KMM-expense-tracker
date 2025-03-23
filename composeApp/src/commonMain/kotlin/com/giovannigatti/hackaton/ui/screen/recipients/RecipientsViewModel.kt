package com.giovannigatti.hackaton.ui.screen.recipients

import androidx.lifecycle.ViewModel
import com.giovannigatti.hackaton.repository.RecipientRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipientsViewModel(
    private val recipientRepository: RecipientRepository
): ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _uiState = MutableStateFlow(RecipientsUiState())
    val uiState: StateFlow<RecipientsUiState> = _uiState

    fun loadRecipients() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    recipients = recipientRepository.getAll()
                )
            }
        }
    }

    fun refresh() {
        loadRecipients()
    }
}