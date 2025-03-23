package com.giovannigatti.hackaton

import com.giovannigatti.hackaton.database.getBudgetDao
import com.giovannigatti.hackaton.database.getCategoryDao
import com.giovannigatti.hackaton.database.getRecipientDao
import com.giovannigatti.hackaton.repository.TransactionRepository
import com.giovannigatti.hackaton.database.getRoomDatabase
import com.giovannigatti.hackaton.database.getTransactionDao
import com.giovannigatti.hackaton.repository.BudgetRepository
import com.giovannigatti.hackaton.repository.CategoryRepository
import com.giovannigatti.hackaton.repository.RecipientRepository
import com.giovannigatti.hackaton.ui.screen.categories.CategoriesViewModel
import com.giovannigatti.hackaton.ui.screen.charts.ChartsViewModel
import com.giovannigatti.hackaton.ui.screen.edit_category.EditCategoryViewModel
import com.giovannigatti.hackaton.ui.screen.edit_recipient.EditRecipientViewModel
import com.giovannigatti.hackaton.ui.screen.edit_transaction.EditTransactionViewModel
import com.giovannigatti.hackaton.ui.screen.home.HomeViewModel
import com.giovannigatti.hackaton.ui.screen.recipients.RecipientsViewModel
import com.giovannigatti.hackaton.ui.screen.transactions.TransactionsViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.dsl.KoinAppDeclaration

//fun initKoin(config: KoinAppDeclaration? = null) =
//    startKoin {
//        config?.invoke(this)
//        modules(
//            platformModule(),
//            provideDatabaseModule,
//            appModule
//        )
//    }

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            platformModule(),
            provideDatabaseModule,
            provideRepositoryModule,
            appModule,
        )
    }

val provideDatabaseModule = module {
    single { getRoomDatabase(get()) } // AppDatabase

    // DAO
    single { getTransactionDao(get()) }
    single { getCategoryDao(get()) }
    single { getRecipientDao(get()) }
    single { getBudgetDao(get()) }
}


val provideRepositoryModule = module {
    single { CategoryRepository(get()) }
    single { RecipientRepository(get()) }
    single { BudgetRepository(get()) }
    single {
        TransactionRepository(
            dao = get(),
            categoryRepository = get(),
            recipientRepository = get()
        )
    }
}

val appModule = module {

    single { HomeViewModel(get()) }
    single { EditTransactionViewModel(
        get(),
        recipientRepository = get(),
        categoryRepository = get()
    ) }
    single { TransactionsViewModel(get()) }
    single { RecipientsViewModel(get()) }
    single { EditRecipientViewModel(get()) }
    single { EditCategoryViewModel(get()) }
    single { CategoriesViewModel(get()) }
    single { ChartsViewModel(get()) }


}
