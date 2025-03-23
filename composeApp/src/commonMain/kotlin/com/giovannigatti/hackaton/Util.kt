package com.giovannigatti.hackaton

import androidx.compose.material3.TextFieldDefaults
import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun extractMonthFromDate(date: LocalDate): String {
    return date.month.name.lowercase().replaceFirstChar { it.uppercase() }
}

fun extractShortMonthFromDate(date: LocalDate): String {
    return date.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
}

fun extractDayFromDate(date: LocalDate): String {
    return date.dayOfMonth.toString().padStart(2, '0')
}

fun extractYearFromDate(date: LocalDate): String {
    return date.year.toString()
}

fun formatLocalDateForUi(date: LocalDate): String {
    val day = date.dayOfMonth.toString().padStart(2, '0')
    val month = date.monthNumber.toString().padStart(2, '0')
    val year = date.year.toString()

    return "$day/$month/$year"
}

fun formatDoubleAsEuro(amount: Double): String {
    val rounded = (amount * 100).toInt() / 100.0
    val parts = rounded.toString().split(".")
    val euros = parts[0]
    val cents = parts.getOrNull(1)?.padEnd(2, '0')?.take(2) ?: "00"
    return "€ $euros.$cents"
}


fun List<Transaction>.groupByDate(): Map<LocalDate, List<Transaction>> {
    return this.groupBy { it.date }
}

fun LocalDate.toMillisAtNoon(): Long {
    val dateTime = this.atTime(LocalTime(hour = 12, minute = 0))
    val instant = dateTime.toInstant(TimeZone.currentSystemDefault())
    return instant.toEpochMilliseconds()
}

fun millisToLocalDate(millis: Long): LocalDate {
    val instant = Instant.fromEpochMilliseconds(millis)
    return instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
}

fun List<Transaction>.groupByCategory(): Map<Category?, List<Transaction>> {
    return this.groupBy { it.category }
}

fun List<Transaction>.groupByRecipient(): Map<Recipient?, List<Transaction>> {
    return this.groupBy { it.recipient }
}

data class YearMonth(val year: Int, val month: Int)


fun List<Transaction>.groupByMonth(): Map<YearMonth, List<Transaction>> {
    return this.groupBy { YearMonth(it.date.year, it.date.monthNumber) }
}
