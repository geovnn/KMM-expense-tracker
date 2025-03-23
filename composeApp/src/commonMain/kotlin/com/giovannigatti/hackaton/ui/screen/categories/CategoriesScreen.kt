package com.giovannigatti.hackaton.ui.screen.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.giovannigatti.hackaton.model.Category
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.ui.composable.CategoryRow
import com.giovannigatti.hackaton.ui.composable.RecipientRow
import com.giovannigatti.hackaton.ui.screen.recipients.RecipientsViewModel

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel,
    goToEditCategory: (Category) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    Box() {
        Column(modifier = Modifier) {
            LazyColumn {

                items(items = uiState.categories, key = {it.id}) { category ->
                    Column(
                        modifier = Modifier.padding(1.dp)
                    ) {
                        CategoryRow(
                            modifier = Modifier,
                            category= category,
                            onClick = { goToEditCategory(category) }
                        )
                    }
                }


                if (uiState.categories.isEmpty()) {
                    item {
                        Text(
                            text = "No categories found",
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
                onClick = { goToEditCategory(
                    Category(
                        id = 0,
                        name = "",
                    )
                ) },
            ) {
                Icon(Icons.Filled.Add, "New Category")
            }
        }

    }

}

