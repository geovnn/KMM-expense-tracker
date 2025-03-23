package com.giovannigatti.hackaton.ui.screen.edit_recipient

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.giovannigatti.hackaton.formatLocalDateForUi
import com.giovannigatti.hackaton.millisToLocalDate
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.model.TransactionType
import com.giovannigatti.hackaton.toMillisAtNoon
import com.giovannigatti.hackaton.ui.composable.ClickableOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipientScreen(
    modifier: Modifier = Modifier,
    viewModel: EditRecipientViewModel,
    recipient: Recipient,
    onClose: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.shouldClose) {
        if (uiState.shouldClose) {
            onClose()
        }
    }

    var name by remember { mutableStateOf(recipient.name) }

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
                    text = if (recipient.id.toInt()==0) "New Recipient" else "Edit Recipient",
                    style = MaterialTheme.typography.titleLarge
                )
                FilledIconButton(
                    modifier = Modifier.fillMaxHeight().padding(5.dp).width(100.dp),
                    shape = MaterialTheme.shapes.large,
                    enabled = name.isNotBlank(),
                    onClick = {
                        viewModel.saveRecipient(
                            Recipient(
                                id = recipient.id,
                                name = name,
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

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 2.dp),
                value = name,
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) } ,
                label = { Text("Name") },
                onValueChange = { name=it },
            )
        }
    }

}