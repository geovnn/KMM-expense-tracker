package com.giovannigatti.hackaton.repository

import com.giovannigatti.hackaton.database.dao.RecipientDao
import com.giovannigatti.hackaton.database.entity.RecipientEntity
import com.giovannigatti.hackaton.database.toEntity
import com.giovannigatti.hackaton.database.toModel
import com.giovannigatti.hackaton.model.Recipient

class RecipientRepository(private val dao: RecipientDao) {

    suspend fun insert(recipient: Recipient): Long = dao.insert(recipient.toEntity())

    suspend fun getAll(): List<Recipient> = dao.getAll().map { it.toModel() }

    suspend fun getById(id: Long): Recipient? = dao.getById(id)?.toModel()

    suspend fun delete(recipient: Recipient) = dao.delete(recipient.toEntity())

    suspend fun deleteAll() = dao.deleteAll()
}
