package com.giovannigatti.hackaton

import org.koin.dsl.module
import androidx.room.RoomDatabase
import com.giovannigatti.hackaton.database.AppDatabase

actual fun platformModule() = module {
    single<RoomDatabase.Builder<AppDatabase>> {
        getDatabaseBuilder() // oppure throw NotImplementedError()
    }
}

fun initKoinIos() {
    initKoin {
        modules(
            platformModule(),
            provideDatabaseModule,
            provideRepositoryModule,
            appModule
        )
    }
}