package com.giovannigatti.hackaton.ui.screen.edit_category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.giovannigatti.hackaton.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: EditCategoryViewModel,
    category: Category,
    onClose: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.shouldClose) {
        if (uiState.shouldClose) {
            onClose()
        }
    }

    var name by remember { mutableStateOf(category.name) }

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
                    text = if (category.id.toInt()==0) "New Category" else "Edit Category",
                    style = MaterialTheme.typography.titleLarge
                )
                FilledIconButton(
                    modifier = Modifier.fillMaxHeight().padding(5.dp).width(100.dp),
                    shape = MaterialTheme.shapes.large,
                    enabled = name.isNotBlank(),
                    onClick = {
                        viewModel.saveRecipient(
                            Category(
                                id = category.id,
                                name = name,
                                color = null
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
                leadingIcon = { Icon(Icons.Filled.Label, contentDescription = null) } ,
                label = { Text("Name") },
                onValueChange = { name=it },
            )
        }
    }

}