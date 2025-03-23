package com.giovannigatti.hackaton.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.giovannigatti.hackaton.formatDoubleAsEuro
import com.giovannigatti.hackaton.model.Recipient
import com.giovannigatti.hackaton.model.Transaction
import com.giovannigatti.hackaton.model.TransactionType

@Composable
fun RecipientRow(
    modifier: Modifier = Modifier,
    recipient: Recipient,
    onClick : () -> Unit
){
    Card(
        modifier = modifier,
        onClick = { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    ) {
        Column(modifier = Modifier.height(50.dp).padding(5.dp).fillMaxWidth()) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Filled.Person,
                    contentDescription = null
                )
                Spacer(modifier= Modifier.width(5.dp))
                Text(
                    text=recipient.name,
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }



}