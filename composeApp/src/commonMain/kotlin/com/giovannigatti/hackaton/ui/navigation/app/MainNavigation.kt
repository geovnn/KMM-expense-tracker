package com.giovannigatti.hackaton.ui.navigation.app

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.model.TransactionType
import com.giovannigatti.hackaton.ui.navigation.screen.Navigation
import com.giovannigatti.hackaton.ui.screen.edit_category.EditCategoryScreen
import com.giovannigatti.hackaton.ui.screen.edit_category.EditCategoryViewModel
import com.giovannigatti.hackaton.ui.screen.edit_recipient.EditRecipientScreen
import com.giovannigatti.hackaton.ui.screen.edit_recipient.EditRecipientViewModel
import com.giovannigatti.hackaton.ui.screen.edit_transaction.EditTransactionScreen
import com.giovannigatti.hackaton.ui.screen.edit_transaction.EditTransactionViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.koinInject

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    val coroutineScope = rememberCoroutineScope()
    var transactionToEdit by remember { mutableStateOf<Transaction?>(null) }
    var recipientToEdit by remember { mutableStateOf<Recipient?>(null) }
    var categoryToEdit by remember { mutableStateOf<Category?>(null) }

    NavHost(
        navController = navController,
        startDestination = AppNavigationScreen.ScreenNavigation.name,
        modifier = Modifier
            .fillMaxSize(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = AppNavigationScreen.ScreenNavigation.name) {
            Navigation(
                editTransaction={
                    transactionToEdit=it
                    navController.navigate(AppNavigationScreen.EditTransaction.name)
                },
                editRecipient = {
                    recipientToEdit=it
                    navController.navigate(AppNavigationScreen.EditRecipient.name)
                },
                editCategory = {
                    categoryToEdit=it
                    navController.navigate(AppNavigationScreen.EditCategory.name)
                }
            )
        }
        composable(
            route = AppNavigationScreen.EditTransaction.name,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        250, easing = LinearEasing
                    )
                ) +
                        slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        250, easing = LinearEasing
                    )
                ) +
                        slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
//            val transaction = transactionToEdit
            val transaction by remember(transactionToEdit) { mutableStateOf(transactionToEdit?:Transaction(
                id = 0,
                amount = 0.0,
                description = null,
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
                category = null,
                type = TransactionType.EXPENSE,
                recipient = null
            )) }
            val viewModel: EditTransactionViewModel = koinInject()
            EditTransactionScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onClose = {
                    transactionToEdit=null
                    navController.popBackStack()
                    viewModel.resetOnClose()
                },
                transaction = transaction
            )

        }
        composable(
            route = AppNavigationScreen.EditRecipient.name,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        250, easing = LinearEasing
                    )
                ) +
                        slideIntoContainer(
                            animationSpec = tween(300, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        250, easing = LinearEasing
                    )
                ) +
                        slideOutOfContainer(
                            animationSpec = tween(300, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
            }) {
            val recipient by remember(recipientToEdit) { mutableStateOf(recipientToEdit?:Recipient(
                id = 0,
                name = ""
            )) }
//            val recipient = recipientToEdit
            val viewModel: EditRecipientViewModel = koinInject()
            EditRecipientScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onClose = {
                    recipientToEdit=null
                    navController.popBackStack()
                    viewModel.resetOnClose()
                },
                recipient = recipient
            )

        }
        composable(
            route = AppNavigationScreen.EditCategory.name,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        250, easing = LinearEasing
                    )
                ) +
                        slideIntoContainer(
                            animationSpec = tween(300, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        250, easing = LinearEasing
                    )
                ) +
                        slideOutOfContainer(
                            animationSpec = tween(300, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
            }
        ) {
            val category by remember(categoryToEdit) { mutableStateOf(categoryToEdit?:Category(
                id = 0,
                name = ""
            )) }
//            val category = categoryToEdit
            val viewModel: EditCategoryViewModel = koinInject()
            EditCategoryScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onClose = {
                    recipientToEdit=null
                    navController.popBackStack()
                    viewModel.resetOnClose()
                },
                category = category
            )

        }
    }
}