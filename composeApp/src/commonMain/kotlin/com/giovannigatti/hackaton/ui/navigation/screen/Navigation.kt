package com.giovannigatti.hackaton.ui.navigation.screen

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.ui.screen.categories.CategoriesScreen
import com.giovannigatti.hackaton.ui.screen.categories.CategoriesViewModel
import com.giovannigatti.hackaton.ui.screen.charts.ChartsScreen
import com.giovannigatti.hackaton.ui.screen.charts.ChartsViewModel
import com.giovannigatti.hackaton.ui.screen.home.HomeScreen
import com.giovannigatti.hackaton.ui.screen.home.HomeViewModel
import com.giovannigatti.hackaton.ui.screen.recipients.RecipientsScreen
import com.giovannigatti.hackaton.ui.screen.recipients.RecipientsViewModel
import com.giovannigatti.hackaton.ui.screen.transactions.TransactionsScreen
import com.giovannigatti.hackaton.ui.screen.transactions.TransactionsViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    editTransaction: (Transaction) -> Unit,
    editRecipient: (Recipient) -> Unit,
    editCategory: (Category) -> Unit,

    ) {
    val coroutineScope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Home.name
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val fadeAnimationDuration = 250
    MainAppDrawer(
        drawerState = drawerState,
        navController = navController,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
//            topBar = {
//                MainTopAppBar(
//                    currentScreen = currentScreen,
//                    canNavigateBack = navController.previousBackStackEntry != null,
//                    navigateUp = { navController.navigateUp() }
//                )
//            },
            bottomBar = {
                MainBottomAppBar(
                    currentScreen = currentScreen,
                    navigate = { screen ->
                        navController.navigate(screen.name) {
                            popUpTo(0){
                                inclusive = true
                                saveState = true
                            }
                        }
                    },
                    openDrawer = { coroutineScope.launch { drawerState.open() }  }
                )
            },
            ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppScreen.Home.name,
                modifier = Modifier
                    .fillMaxSize()
//                .verticalScroll(rememberScrollState())
                    .padding(innerPadding),
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                composable(
                    route = AppScreen.Home.name,
                    enterTransition = {
                        fadeIn(animationSpec = tween(fadeAnimationDuration, easing = LinearEasing))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(fadeAnimationDuration, easing = LinearEasing))
                    }
                ) {
                    val viewModel: HomeViewModel = koinInject()

                    HomeScreen(
                        viewModel = viewModel,
                        goToEditTransaction = { transaction ->
                            editTransaction(transaction)
                        },
                        goToAllTransactions = {
                            navController.navigate(AppScreen.Transactions.name)
                        }
                    )
                }
                composable(
                    route = AppScreen.Transactions.name,
                    enterTransition = {
                        fadeIn(animationSpec = tween(fadeAnimationDuration, easing = LinearEasing))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(fadeAnimationDuration, easing = LinearEasing))
                    }
                ) {
                    val viewModel: TransactionsViewModel = koinInject()

                    TransactionsScreen(
                        viewModel = viewModel,
                        goToEditTransaction = { transaction ->
                            editTransaction(transaction)
                        }
                    )
                }

                composable(route = AppScreen.Charts.name,
                    enterTransition = {
                        fadeIn(animationSpec = tween(fadeAnimationDuration, easing = LinearEasing))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(fadeAnimationDuration, easing = LinearEasing))
                    }) {
                    val viewModel: ChartsViewModel = koinInject()

                    ChartsScreen(
                        viewModel = viewModel,
                    )
                }

                composable(route = AppScreen.Recipients.name,
                    enterTransition = {
                        fadeIn(animationSpec = tween(fadeAnimationDuration, easing = LinearEasing))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(fadeAnimationDuration, easing = LinearEasing))
                    }) {
                    val viewModel: RecipientsViewModel = koinInject()

                    RecipientsScreen(
                        viewModel = viewModel,
                        goToEditRecipient = { recipient ->
                            editRecipient(recipient)
                        }
                    )
                }
                composable(route = AppScreen.Categories.name,
                    enterTransition = {
                        fadeIn(animationSpec = tween(fadeAnimationDuration, easing = LinearEasing))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(fadeAnimationDuration, easing = LinearEasing))
                    }) {
                    val viewModel: CategoriesViewModel = koinInject()

                    CategoriesScreen(
                        viewModel = viewModel,
                        goToEditCategory = { category ->
                            editCategory(category)
                        }
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen.title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null //stringResource(Res.string.back_button)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBottomAppBar(
    modifier: Modifier = Modifier,
    currentScreen: AppScreen,
    navigate: (AppScreen) -> Unit,
    openDrawer: () -> Unit,
) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            IconButton(onClick = { openDrawer() }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
            Spacer(modifier=Modifier.weight(1f))
            BottomAppBarIcon(
                Icons.Filled.Home,
                AppScreen.Home.title,
                currentScreen == AppScreen.Home
            ) {
                navigate(AppScreen.Home)
            }
            BottomAppBarIcon(
                Icons.Filled.CurrencyExchange,
                AppScreen.Transactions.title,
                currentScreen == AppScreen.Transactions
            ) {
                navigate(AppScreen.Transactions)
            }
//            BottomAppBarIcon(
//                Icons.Filled.DateRange,
//                AppScreen.Budgets.title,
//                currentScreen == AppScreen.Budgets
//            ) {
//                navigate(AppScreen.Budgets)
//            }
            BottomAppBarIcon(
                Icons.Filled.PieChart,
                AppScreen.Charts.title,
                currentScreen == AppScreen.Charts
            ) {
                navigate(AppScreen.Charts)
            }
        }
    )
}

@Composable
fun BottomAppBarIcon(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(2.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(
                    color = if (isSelected)
                        MaterialTheme.colorScheme.secondaryContainer else
                        Color.Transparent,
                    shape = MaterialTheme.shapes.large
                ),
            onClick = { onClick() }
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                imageVector = icon,
                contentDescription = "Localized description",
                tint = if (isSelected)
                    MaterialTheme.colorScheme.onSecondaryContainer else
                    MaterialTheme.colorScheme.onSurface,
            )
        }
        Text(
            text = label,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.labelLarge.fontSize,
            fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
            fontFamily = MaterialTheme.typography.labelLarge.fontFamily,
            fontStyle = MaterialTheme.typography.labelLarge.fontStyle
        )
    }

}

@Composable
fun MainAppDrawer(
    drawerState : DrawerState,
    navController: NavHostController,
    drawerContent: @Composable () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
//                Text("Drawer title", modifier = Modifier.padding(16.dp))
//                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Recipients") },
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Recipients") },
                    selected = navController.currentDestination?.route== AppScreen.Recipients.name,
                    onClick = {
                        navController.navigate(AppScreen.Recipients.name)
                        coroutineScope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Categories") },
                    icon = { Icon(Icons.Filled.Label, contentDescription = "Categories") },
                    selected = navController.currentDestination?.route== AppScreen.Categories.name,
                    onClick = {
                        navController.navigate(AppScreen.Categories.name)
                        coroutineScope.launch { drawerState.close() }
                    }
                )
                Spacer(modifier = Modifier.weight(1f))


            }
        }
    ) {
        drawerContent()
    }
}