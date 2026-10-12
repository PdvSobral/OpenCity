package com.example.opencity.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun TopBar(
    tittle: String,
    description: String? = null,
    showBackArrow: Boolean = false,
    onBackClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 0.dp, bottom = 6.dp, start = 0.dp, end = 0.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer).padding(horizontal = 16.dp, vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackArrow) {
            IconButton(
                modifier = Modifier.padding(all = 0.dp).size(50.dp),
                onClick = onBackClick
            ) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        }
        Column () {
            if (description != null) {
                Text(
                    text = tittle,
                    modifier = Modifier.padding(top = 12.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = description,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    text = tittle,
                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}
