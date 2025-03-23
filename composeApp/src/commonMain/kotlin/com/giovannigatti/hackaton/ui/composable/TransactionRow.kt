package com.giovannigatti.hackaton.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.giovannigatti.hackaton.extractDayFromDate
import com.giovannigatti.hackaton.extractShortMonthFromDate
import com.giovannigatti.hackaton.extractYearFromDate
import com.giovannigatti.hackaton.formatDoubleAsEuro
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.model.TransactionType

@Composable
fun TransactionRow(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    onClick : () -> Unit
){
    Card(
        modifier = modifier,
        onClick = { onClick() },
        colors = if (transaction.type == TransactionType.EXPENSE)
            CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
            ) else CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
    ) {
        Column(modifier = Modifier) {
            Row(modifier = Modifier.padding(3.dp)) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            imageVector = Icons.Filled.Person,
                            contentDescription = null
                        )
                        Spacer(modifier=Modifier.width(5.dp))
                        Text(
                            text=transaction.recipient?.name?:"No recipient",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            imageVector = Icons.Filled.Label,
                            contentDescription = null
                        )
                        Spacer(modifier=Modifier.width(5.dp))
                        Text(
                            text=transaction.category?.name?:"No category",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                }
                Text(
                    text= formatDoubleAsEuro(transaction.amount),
//                    color = if(transaction.type == TransactionType.INCOME) Color.Unspecified else MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Right,
                    style = MaterialTheme.typography.headlineLarge,
                )

            }
            if (!transaction.description.isNullOrBlank()) {
                HorizontalDivider()
                Text(
                    modifier = Modifier.padding(3.dp),
                    text=transaction.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }



}