package com.giovannigatti.hackaton.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.math.max

@Composable
fun ClickableOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    icon: ImageVector? = null,
    label: String? = null,
    onClick: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .clickable { onClick() },
        value = value,
        onValueChange = {  },
        enabled = false,
        maxLines = 1,
        label = { if (label != null) { Text(label) }},
        leadingIcon = { if (icon != null) { Icon(icon, contentDescription = null) } },
        trailingIcon = { Icon(Icons.Filled.MenuOpen, contentDescription = null) },
        colors = TextFieldDefaults.colors(
            disabledTextColor = TextFieldDefaults.colors().unfocusedTextColor,
            disabledPrefixColor = TextFieldDefaults.colors().unfocusedPrefixColor,
            disabledSuffixColor = TextFieldDefaults.colors().unfocusedSuffixColor,
            disabledLabelColor = TextFieldDefaults.colors().unfocusedLabelColor,
            disabledContainerColor = Color.Transparent,// TextFieldDefaults.colors().unfocusedContainerColor,
            disabledIndicatorColor = TextFieldDefaults.colors().unfocusedIndicatorColor,
            disabledPlaceholderColor = TextFieldDefaults.colors().unfocusedPlaceholderColor,
            disabledLeadingIconColor = TextFieldDefaults.colors().unfocusedLeadingIconColor,
            disabledTrailingIconColor = TextFieldDefaults.colors().unfocusedTrailingIconColor,
            disabledSupportingTextColor = TextFieldDefaults.colors().unfocusedSupportingTextColor
        )
    )
}