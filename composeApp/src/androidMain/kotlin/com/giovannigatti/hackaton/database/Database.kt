package com.giovannigatti.hackaton.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    println("📁 DB will be stored in: ${context.getDatabasePath("expense_tracker.db").absolutePath}")
    return Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "expense_tracker.db"
    )
}
//fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
//    println("⚠️ Using in-memory Room database")
//    return Room.inMemoryDatabaseBuilder(
//        context.applicationContext,
//        AppDatabase::class.java
//    )
//}
//actual object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
//    actual override fun initialize(): AppDatabase = error("Should not be called directly.")
//}
