package com.giovannigatti.hackaton.ui.screen.recipients

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.model.TransactionType
import com.giovannigatti.hackaton.ui.composable.ClickableOutlinedTextField
import com.giovannigatti.hackaton.ui.composable.RecipientRow
import com.giovannigatti.hackaton.ui.composable.TransactionRow
import com.giovannigatti.hackaton.ui.screen.transactions.TransactionsGroupType
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipientsScreen(
    viewModel: RecipientsViewModel,
    goToEditRecipient: (Recipient) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    Box() {
        Column(modifier = Modifier) {
            LazyColumn {

                items(items = uiState.recipients, key = {it.id}) { recipient ->
                    Column(
                        modifier = Modifier.padding(1.dp)
                    ) {
                        RecipientRow(
                            modifier = Modifier,
                            recipient= recipient,
                            onClick = { goToEditRecipient(recipient) }
                        )
                    }
                }


                if (uiState.recipients.isEmpty()) {
                    item {
                        Text(
                            text = "No recipients found",
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
                onClick = { goToEditRecipient(
                    Recipient(
                        id = 0,
                        name = "",
                    )
                ) },
            ) {
                Icon(Icons.Filled.Add, "New Recipient")
            }
        }

    }

}

