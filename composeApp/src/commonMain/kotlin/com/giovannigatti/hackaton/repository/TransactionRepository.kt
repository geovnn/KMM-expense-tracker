package com.giovannigatti.hackaton.repository

import com.giovannigatti.hackaton.database.dao.TransactionDao
import com.giovannigatti.hackaton.database.entity.TransactionWithRelations
import com.giovannigatti.hackaton.database.toEntity
import com.giovannigatti.hackaton.database.toModel
import com.giovannigatti.hackaton.model.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class TransactionRepository(
    private val dao: TransactionDao,
    private val categoryRepository: CategoryRepository,
    private val recipientRepository: RecipientRepository
    ) {

//    init {
//        val coroutineScope = CoroutineScope(Dispatchers.IO)
//        coroutineScope.launch {
//            seedInitialData(categoryRepository,recipientRepository)
//        }
//    }

    suspend fun insert(transaction: Transaction): Long = dao.insert(transaction.toEntity())

    suspend fun getAll(): List<Transaction> = dao.getAll().map {
        it.toModel(
            category = it.categoryId?.let { it1 -> categoryRepository.getById(it1) },
            recipient = it.recipientId?.let { it1 -> recipientRepository.getById(it1) }
        )
    }

    suspend fun getAllWithRelations(): List<TransactionWithRelations> = dao.getAllWithRelations()

    suspend fun getById(id: Long): Transaction? {
        val transaction = dao.getById(id)
        return transaction?.toModel(
            category = transaction.categoryId?.let { categoryRepository.getById(it) },
            recipient = transaction.recipientId?.let { recipientRepository.getById(it) }
        )

    }

    suspend fun getByIdWithRelations(id: Long): TransactionWithRelations? = dao.getByIdWithRelations(id)

    suspend fun delete(transaction: Transaction) = dao.delete(transaction.toEntity())

    suspend fun deleteAll() = dao.deleteAll()
}
