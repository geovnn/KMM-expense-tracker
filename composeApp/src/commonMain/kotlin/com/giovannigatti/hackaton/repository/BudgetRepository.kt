package com.giovannigatti.hackaton.repository

import com.giovannigatti.hackaton.database.dao.BudgetDao
import com.giovannigatti.hackaton.database.entity.BudgetEntity
import com.giovannigatti.hackaton.database.toEntity
import com.giovannigatti.hackaton.database.toModel
import com.giovannigatti.hackaton.model.Budget

class BudgetRepository(private val dao: BudgetDao) {

    suspend fun insert(budget: Budget): Long = dao.insert(budget.toEntity())

    suspend fun getAll(): List<Budget> = dao.getAll().map { it.toModel() }

    suspend fun getById(id: Long): Budget? = dao.getById(id)?.toModel()

    suspend fun delete(budget: Budget) = dao.delete(budget.toEntity())

    suspend fun deleteAll() = dao.deleteAll()
}
