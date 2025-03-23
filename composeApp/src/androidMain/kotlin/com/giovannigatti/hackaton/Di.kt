package com.giovannigatti.hackaton

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.giovannigatti.hackaton.database.AppDatabase
import com.giovannigatti.hackaton.database.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

//actual fun platformModule() = module {
//    single<RoomDatabase.Builder<AppDatabase>> {
//        Room.databaseBuilder(
//            androidContext(),
//            AppDatabase::class.java,
//            "expense_tracker.db"
//        )
//    }
//}

actual fun platformModule() = module {
    single {
        getDatabaseBuilder(androidContext())
    }
}