package com.example.opencity.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


//TODO: Add a boolean input to show a navigation back arrow.
@Composable
fun TopBar(
    tittle: String,
    description: String? = null
) {
    Column (
        Modifier.fillMaxWidth().padding(top = 0.dp, bottom = 6.dp, start = 0.dp, end = 0.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer).padding(horizontal = 16.dp, vertical = 0.dp)
    ) {
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
