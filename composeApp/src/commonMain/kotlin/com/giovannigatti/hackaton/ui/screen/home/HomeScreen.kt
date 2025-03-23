package com.giovannigatti.hackaton.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.giovannigatti.hackaton.extractDayFromDate
import com.giovannigatti.hackaton.extractMonthFromDate
import com.giovannigatti.hackaton.extractShortMonthFromDate
import com.giovannigatti.hackaton.extractYearFromDate
import com.giovannigatti.hackaton.formatDoubleAsEuro
import com.giovannigatti.hackaton.formatLocalDateForUi
import com.giovannigatti.hackaton.groupByDate
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.model.TransactionType
import com.giovannigatti.hackaton.ui.composable.TransactionRow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    goToEditTransaction: (Transaction) -> Unit,
    goToAllTransactions: () -> Unit
    ) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    Box(
        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceDim)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {

            Text(
                modifier = Modifier.fillMaxWidth(1f).padding(10.dp),
                text="Expense Tracker",
                style = MaterialTheme.typography.headlineLarge,
                )
            ElevatedCard() {

                Column (
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                ) {

                    Row(
                        modifier = Modifier.padding(5.dp),
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text="Income this month:",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            modifier = Modifier,
                            text= formatDoubleAsEuro(uiState.totalIncome),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Row(
                        modifier = Modifier.padding(5.dp),
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Expenses this month:",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.error

                        )
                        Text(
                            modifier = Modifier,
                            text = formatDoubleAsEuro(uiState.totalExpense),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Row(
                        modifier = Modifier.padding(5.dp),
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text="Difference:",
                            style = MaterialTheme.typography.titleLarge,

                        )
                        Text(
                            modifier = Modifier,
                            text = formatDoubleAsEuro(uiState.totalDifference),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                }
            }
            Spacer(Modifier.padding(5.dp))
            ElevatedCard() {
                Column {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text="Recent transactions:",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            modifier = Modifier.clickable { goToAllTransactions() },
                            text="View all",
                            color = MaterialTheme.colorScheme.tertiary,
                            textDecoration = TextDecoration.Underline,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }


                    val list by remember(uiState.recentTransactions) { mutableStateOf(uiState.recentTransactions.groupByDate()) }
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        userScrollEnabled = false
                    ) {
                        list.forEach { (date, transactionList) ->

                            stickyHeader {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.surfaceDim)
//                                .alpha(0.3f)
                                        .padding(5.dp)
                                ) {

                                    Text(
                                        modifier = Modifier.fillMaxSize(),
                                        text =
                                            "${extractDayFromDate(date)} " +
                                                    "${extractShortMonthFromDate(date)} " +
                                                    extractYearFromDate(date),
                                        textAlign = TextAlign.Start,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }

                            items(items = transactionList, key = {it.id}) { transaction ->
                                Column(
                                    modifier = Modifier.padding(5.dp)
                                ) {
                                    TransactionRow(
                                        modifier = Modifier,
                                        transaction= transaction,
                                        onClick = { goToEditTransaction(transaction) }
                                    )
                                }
                            }
                        }
                        if (uiState.recentTransactions.isEmpty()) {
                            item {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    text="No transactions found")
                            }
                        }
                    }

                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
        ) {
            FloatingActionButton(
                modifier = Modifier.size(75.dp),
                onClick = { goToEditTransaction(
                    Transaction(
                        id = 0,
                        amount = 0.0,
                        description = null,
                        date = Clock.System.now()
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                            .date,
                        category = null,
                        type = TransactionType.EXPENSE,
                        recipient = null

                    )
                ) },
            ) {
                Icon(Icons.Filled.Add, "New Transaction")
            }
        }

    }

}

