package com.giovannigatti.hackaton.repository

import com.giovannigatti.hackaton.data.database.entity.CategoryEntity
import com.giovannigatti.hackaton.database.dao.CategoryDao
import com.giovannigatti.hackaton.database.toEntity
import com.giovannigatti.hackaton.database.toModel
import com.giovannigatti.hackaton.model.Category

class CategoryRepository(private val dao: CategoryDao) {

    suspend fun insert(category: Category): Long = dao.insert(category.toEntity())

    suspend fun getAll(): List<Category> = dao.getAll().map { it.toModel() }

    suspend fun getById(id: Long): Category? = dao.getById(id)?.toModel()

    suspend fun delete(category: Category) = dao.delete(category.toEntity())

    suspend fun deleteAll() = dao.deleteAll()
}