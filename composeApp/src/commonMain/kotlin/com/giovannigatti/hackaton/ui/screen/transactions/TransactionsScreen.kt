package com.giovannigatti.hackaton.ui.screen.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.giovannigatti.hackaton.extractDayFromDate
import com.giovannigatti.hackaton.extractShortMonthFromDate
import com.giovannigatti.hackaton.extractYearFromDate
import com.giovannigatti.hackaton.groupByCategory
import com.giovannigatti.hackaton.groupByDate
import com.giovannigatti.hackaton.groupByRecipient
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.model.TransactionType
import com.giovannigatti.hackaton.ui.composable.ClickableOutlinedTextField
import com.giovannigatti.hackaton.ui.composable.TransactionRow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionsScreen(
    viewModel: TransactionsViewModel,
    goToEditTransaction: (Transaction) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    var groupType by remember { mutableStateOf(TransactionsGroupType.DAY) }
    Box() {
        Column(modifier = Modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                var expanded by remember { mutableStateOf(false) }

                Box(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.TopEnd)) {
                    ClickableOutlinedTextField(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 2.dp),
                        value = groupType.title,
                        label = "Group by",
                        icon = when (groupType) {
                            TransactionsGroupType.CATEGORY -> Icons.Default.Label
                            TransactionsGroupType.RECIPIENT -> Icons.Default.Person
                            TransactionsGroupType.DAY -> Icons.Default.CalendarMonth
                        },
                        onClick = { expanded = true  },
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        TransactionsGroupType.entries.forEach {
                            DropdownMenuItem(
                                text = { Text(it.title) },
                                onClick = { groupType = it
                                          expanded=false},
                                leadingIcon = {
                                    Icon(
                                        when (it) {
                                            TransactionsGroupType.CATEGORY -> Icons.Default.Label
                                            TransactionsGroupType.RECIPIENT -> Icons.Default.Person
                                            TransactionsGroupType.DAY -> Icons.Default.CalendarMonth
                                        }, contentDescription = null
                                    )
                                },
                                trailingIcon = { }
                            )
                            if (it != TransactionsGroupType.values().last()) {
                                HorizontalDivider()
                            }
                        }

                    }
                }
            }
            val listByCategory by remember(uiState.recentTransactions) { mutableStateOf(uiState.recentTransactions.groupByCategory()) }
            val listByRecipient by remember(uiState.recentTransactions) { mutableStateOf(uiState.recentTransactions.groupByRecipient()) }
            val listByDate by remember(uiState.recentTransactions) { mutableStateOf(uiState.recentTransactions.groupByDate()) }
            LazyColumn {
                when (groupType) {
                    TransactionsGroupType.CATEGORY -> {
                        listByCategory.forEach { (category, transactionList) ->

                            stickyHeader {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.surfaceDim)
//                                .alpha(0.3f)
                                        .padding(5.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Label,null)
                                        Spacer(Modifier.width(5.dp))
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = category?.name ?: "No category",
                                            textAlign = TextAlign.Start,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    }

                                }
                            }

                            items(items = transactionList, key = {it.id}) { transaction ->
                                Column(
                                    modifier = Modifier.padding(1.dp)
                                ) {
                                    TransactionRow(
                                        modifier = Modifier,
                                        transaction= transaction,
                                        onClick = { goToEditTransaction(transaction) }
                                    )
                                }
                            }
                        }
                    }
                    TransactionsGroupType.RECIPIENT -> {
                        listByRecipient.forEach { (recipient, transactionList) ->

                            stickyHeader {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.surfaceDim)
//                                .alpha(0.3f)
                                        .padding(5.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Person,null)
                                        Spacer(Modifier.width(5.dp))
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = recipient?.name ?: "No Recipient",
                                            textAlign = TextAlign.Start,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    }

                                }
                            }

                            items(items = transactionList, key = {it.id}) { transaction ->
                                Column(
                                    modifier = Modifier.padding(1.dp)
                                ) {
                                    TransactionRow(
                                        modifier = Modifier,
                                        transaction= transaction,
                                        onClick = { goToEditTransaction(transaction) }
                                    )
                                }
                            }
                        }
                    }
                    TransactionsGroupType.DAY -> {
                        listByDate.forEach { (date, transactionList) ->

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
                                    modifier = Modifier.padding(1.dp)
                                ) {
                                    TransactionRow(
                                        modifier = Modifier,
                                        transaction= transaction,
                                        onClick = { goToEditTransaction(transaction) }
                                    )
                                }
                            }
                        }
                    }
                }


                if (uiState.recentTransactions.isEmpty()) {
                    item {
                        Text(
                            text = "No transactions found",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                        )
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

