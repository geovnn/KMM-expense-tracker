package com.giovannigatti.hackaton.ui.screen.recipients

import com.giovannigatti.hackaton.model.Recipient

data class RecipientsUiState(
    val recipients: List<Recipient> = emptyList(),
)