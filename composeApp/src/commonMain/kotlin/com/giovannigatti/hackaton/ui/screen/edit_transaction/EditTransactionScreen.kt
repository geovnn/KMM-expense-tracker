package com.giovannigatti.hackaton.ui.screen.edit_transaction

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.window.DialogProperties
import com.giovannigatti.hackaton.formatLocalDateForUi
import com.giovannigatti.hackaton.millisToLocalDate
import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.model.TransactionType
import com.giovannigatti.hackaton.toMillisAtNoon
import com.giovannigatti.hackaton.ui.composable.ClickableOutlinedTextField
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTransactionScreen(
    modifier: Modifier = Modifier,
    viewModel: EditTransactionViewModel,
    transaction: Transaction,
    onClose: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.shouldClose) {
        if (uiState.shouldClose) {
            onClose()
        }
    }

    var amount by remember { mutableStateOf(transaction.amount.toString()) }
    var description by remember { mutableStateOf(transaction.description?:"") }
    var date by remember { mutableStateOf(transaction.date) }
    var category by remember { mutableStateOf(transaction.category) }
    var type by remember { mutableStateOf(transaction.type) }
    var recipient by remember { mutableStateOf(transaction.recipient) }


    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showRecipientPickerDialog by remember { mutableStateOf(false) }
    var showCategoryPickerDialog by remember { mutableStateOf(false) }

    if (showDatePickerDialog) {
        val datePickerState = rememberDatePickerState(
            initialDisplayMode = DisplayMode.Picker,
            initialDisplayedMonthMillis = date.toMillisAtNoon(),
            initialSelectedDateMillis = date.toMillisAtNoon(),
        )
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {
                showDatePickerDialog=false
            },
            title = { Text(text = "Select date") },
            icon = { Icon(Icons.Filled.CalendarMonth,null) },
            text = { DatePicker(
                modifier = Modifier.fillMaxSize(),
                state = datePickerState) },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selection = datePickerState.selectedDateMillis
                        if (selection != null) {
                            date = millisToLocalDate(selection)
                        }
                        showDatePickerDialog=false
                    },
                    enabled = datePickerState.selectedDateMillis!=null
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePickerDialog=false }
                ) {
                    Text(text = "Cancel",)
                }
            }
        )
    }

    if (showCategoryPickerDialog) {

        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {
                showCategoryPickerDialog=false
            },
            title = { Text(text = "Select category") },
            icon = { Icon(Icons.Filled.Label,null) },
            text = {
                LazyColumn {
                    items(uiState.categoryList) {
                        Row(modifier=Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .height(50.dp)
                            .clickable {
                                category=it
                                showCategoryPickerDialog=false
                            },
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(text =it.name,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        HorizontalDivider()
                    }
                    if (uiState.categoryList.isEmpty()) {
                        item {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text="No categories found",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            },
            confirmButton = { },
            dismissButton = {
                TextButton(
                    onClick = { showCategoryPickerDialog=false }
                ) {
                    Text(text = "Cancel",)
                }
            }
        )
    }

    if (showRecipientPickerDialog) {

        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {
                showRecipientPickerDialog=false
            },
            title = { Text(text = "Select recipient") },
            icon = { Icon(Icons.Filled.Person,null) },
            text = {
                LazyColumn {
                    items(uiState.recipientsList) {
                        Row(modifier=Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .height(50.dp)
                            .clickable {
                                recipient=it
                                showRecipientPickerDialog=false
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(it.name)
                        }
                        HorizontalDivider()
                    }
                    if (uiState.recipientsList.isEmpty()) {
                        item {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text="No recipients found",
                                textAlign = TextAlign.Center
                                )
                        }
                    }
                }
            },
            confirmButton = { },
            dismissButton = {
                TextButton(
                    onClick = { showRecipientPickerDialog=false }
                ) {
                    Text(text = "Cancel",)
                }
            }
        )
    }

    Surface(
        modifier = modifier

    ) {
        Column {
            Row(
                modifier = Modifier.height(75.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = { onClose() }
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Close")
                }
                Text(
                    modifier = Modifier.weight(1f).padding(5.dp),
                    text = if (transaction.id.toInt()==0) "New Transaction" else "Edit Transaction",
                    style = MaterialTheme.typography.titleLarge
                    )
                FilledIconButton(
                    modifier = Modifier.fillMaxHeight().padding(5.dp).width(100.dp),
                    shape = MaterialTheme.shapes.large,
                    enabled = amount.toDoubleOrNull()!=null && amount.toDouble()>0,
                    onClick = {
                        viewModel.saveTransaction(
                            Transaction(
                                id = transaction.id,
                                amount = amount.toDouble(),
                                description = description,
                                date = date,
                                category = category,
                                type = type,
                                recipient = recipient
                            )
                        )
                    },

                ) {
                    Row {
                        Icon(Icons.Filled.Save, contentDescription = "Expense")
                        Spacer(modifier = Modifier.width(5.dp))
                        Text("Save")
                    }
                }
            }
            Row {
                OutlinedIconButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    ),
                    border = if (type==TransactionType.EXPENSE) BorderStroke(width = 3.dp, color =MaterialTheme.colorScheme.primary) else null,
                    shape = MaterialTheme.shapes.large,
                    onClick = { type=TransactionType.EXPENSE },
//                    colors = IconButtonDefaults.outlinedIconButtonColors(
//                        contentColor = MaterialTheme.colorScheme.onError,
//                        containerColor = MaterialTheme.colorScheme.error,
//                    )
                ) {
                    Row {
                        Icon(Icons.Filled.Remove, contentDescription = "Expense")
                        Spacer(modifier = Modifier.width(5.dp))
                        Text("Expense")
                    }
                }
                OutlinedIconButton(
                    modifier = Modifier.weight(1f).padding(5.dp),
                    border = if (type==TransactionType.INCOME) BorderStroke(width = 3.dp, color =MaterialTheme.colorScheme.primary) else null,
                    shape = MaterialTheme.shapes.large,
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    onClick = { type=TransactionType.INCOME },
                ) {
                    Row {
                        Icon(Icons.Filled.Add, contentDescription = "Income")
                        Spacer(modifier = Modifier.width(5.dp))
                        Text("Income")
                    }
                }
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 2.dp),
                value = amount,
                keyboardOptions = KeyboardOptions().copy(
                    keyboardType = KeyboardType.Number
                ),
                maxLines = 1,
                leadingIcon = { Icon(Icons.Filled.Calculate, contentDescription = null) } ,
                label = { Text("Amount") },
                onValueChange = { amount=it },
            )

            ClickableOutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 2.dp),
                value = formatLocalDateForUi(date),
                label = "Date",
                icon = Icons.Filled.CalendarMonth,
                onClick = { showDatePickerDialog=true },
            )

            ClickableOutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 2.dp),
                value = recipient?.name ?: "",
                label = "Recipient",
                icon = Icons.Filled.Person,
                onClick = { showRecipientPickerDialog=true  },
            )

            ClickableOutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 2.dp),
                value = category?.name ?: "",
                label = "Category",
                icon = Icons.Filled.Label,
                onClick = { showCategoryPickerDialog=true  },
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 2.dp),
                value = description,
                minLines = 3,
                leadingIcon = { Icon(Icons.Filled.Description, contentDescription = null) } ,
                label = { Text("Description") },
                onValueChange = { description=it },
            )
        }
    }

}