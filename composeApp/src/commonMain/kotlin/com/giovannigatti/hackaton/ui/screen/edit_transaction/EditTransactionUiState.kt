package com.giovannigatti.hackaton.ui.screen.edit_transaction

import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction

data class EditTransactionUiState(
    val shouldClose: Boolean = false,
    val recipientsList: List<Recipient> = emptyList(),
    val categoryList: List<Category> = emptyList(),
)
