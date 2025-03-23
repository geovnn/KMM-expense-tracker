package com.giovannigatti.hackaton.repository

import androidx.compose.ui.graphics.Color
import com.giovannigatti.hackaton.data.database.entity.CategoryEntity
import com.giovannigatti.hackaton.database.entity.RecipientEntity
import com.giovannigatti.hackaton.database.toModel

suspend fun seedInitialData(
    categoryRepository: CategoryRepository,
    recipientRepository: RecipientRepository
) {
    // Seed Categorie
    val categories = listOf(
        CategoryEntity(name = "Cibo", color = Color(0xFFE57373).value.toLong()),
        CategoryEntity(name = "Trasporti", color = Color(0xFF64B5F6).value.toLong()),
        CategoryEntity(name = "Shopping", color = Color(0xFFBA68C8).value.toLong()),
        CategoryEntity(name = "Salute", color = Color(0xFF81C784).value.toLong()),
        CategoryEntity(name = "Altro", color = Color(0xFF90A4AE).value.toLong())
    )
    categories.map { it.toModel() }.forEach { categoryRepository.insert(it) }

    // Seed Recipient
    val recipients = listOf(
        RecipientEntity(name = "Amazon"),
        RecipientEntity(name = "Supermercato"),
        RecipientEntity(name = "Farmacia"),
        RecipientEntity(name = "Taxi"),
        RecipientEntity(name = "Altro")
    )
    recipients.map { it.toModel() }.forEach { recipientRepository.insert(it) }

}
