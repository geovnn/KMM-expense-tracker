package com.giovannigatti.hackaton

import androidx.room.Room
import androidx.room.RoomDatabase
import com.giovannigatti.hackaton.database.AppDatabase
import com.giovannigatti.hackaton.repository.TransactionRepository
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + "/my_room.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}

class TransactionRepositoryHelper : KoinComponent {
    private val repository: TransactionRepository by inject()
    fun getTransactionRepository(): TransactionRepository = repository
}
