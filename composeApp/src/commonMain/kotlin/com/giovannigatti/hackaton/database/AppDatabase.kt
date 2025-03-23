package com.giovannigatti.hackaton.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.giovannigatti.hackaton.data.database.entity.CategoryEntity
import com.giovannigatti.hackaton.data.database.entity.TransactionEntity
import com.giovannigatti.hackaton.database.dao.BudgetDao
import com.giovannigatti.hackaton.database.dao.CategoryDao
import com.giovannigatti.hackaton.database.dao.RecipientDao
import com.giovannigatti.hackaton.database.dao.TransactionDao
import com.giovannigatti.hackaton.database.entity.BudgetEntity
import com.giovannigatti.hackaton.database.entity.RecipientEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
        RecipientEntity::class,
        BudgetEntity::class
    ],
    version = 1
)
@TypeConverters(AppTypeConverters::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTransactionDao(): TransactionDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getRecipientDao(): RecipientDao
    abstract fun getBudgetDao(): BudgetDao

}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}


fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .addMigrations()
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

fun getTransactionDao(db: AppDatabase): TransactionDao = db.getTransactionDao()
fun getCategoryDao(db: AppDatabase): CategoryDao = db.getCategoryDao()
fun getRecipientDao(db: AppDatabase): RecipientDao = db.getRecipientDao()
fun getBudgetDao(db: AppDatabase):BudgetDao = db.getBudgetDao()