package com.crop2cash.fetcher.presentation.ui.exhibit_listings.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.crop2cash.fetcher.presentation.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    query: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(MaterialTheme.spacing.medium),
        modifier = Modifier
            .padding(MaterialTheme.spacing.medium)
            .fillMaxWidth(),
        placeholder = {
            Text(text = "Search...")
        },
        maxLines = 1,
        singleLine = true
    )
}