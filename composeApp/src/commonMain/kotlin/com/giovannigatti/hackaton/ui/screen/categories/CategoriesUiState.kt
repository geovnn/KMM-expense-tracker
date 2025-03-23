package com.giovannigatti.hackaton.ui.screen.categories

import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.model.Recipient

data class CategoriesUiState(
    val categories: List<Category> = emptyList(),
)