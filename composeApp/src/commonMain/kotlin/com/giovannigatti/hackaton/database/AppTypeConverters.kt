package com.giovannigatti.hackaton.database

import androidx.room.TypeConverter
import com.giovannigatti.hackaton.data.database.entity.CategoryEntity
import com.giovannigatti.hackaton.database.entity.BudgetEntity
import com.giovannigatti.hackaton.database.entity.RecipientEntity
import com.giovannigatti.hackaton.model.Budget
import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.model.TransactionType
import kotlinx.datetime.LocalDate

class AppTypeConverters {

    @TypeConverter
    fun fromEpochDay(value: Int): LocalDate = LocalDate.fromEpochDays(value)

    @TypeConverter
    fun localDateToEpochDay(date: LocalDate): Int = date.toEpochDays()

    @TypeConverter
    fun transactionTypeToString(type: TransactionType): String = type.name

    @TypeConverter
    fun stringToTransactionType(name: String): TransactionType = TransactionType.valueOf(name)
}
