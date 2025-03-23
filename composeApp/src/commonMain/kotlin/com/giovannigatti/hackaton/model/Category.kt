package com.giovannigatti.hackaton.model

import androidx.compose.ui.graphics.Color

data class Category(
    val id: Long,
    val name: String,
    val color: Color? = null
)
