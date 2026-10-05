package com.example.opencity.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    tittle: String,
    description: String? = null
) {
    Column {
        if (description != null) {
            Text(
                text = tittle,
                modifier = Modifier.padding(top = 20.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = description,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            Text(
                text = tittle,
                modifier = Modifier.padding(top = 20.dp, bottom = 16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
