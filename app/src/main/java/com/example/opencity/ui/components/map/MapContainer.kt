package com.example.opencity.ui.components.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.opencity.R


@Composable
fun MapContainer(
    markers: List<Int>? = null,
) {
    Box (
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        Text(
            text = stringResource(id=R.string.map_preview_mainpage),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        // Example map markers
        MarkerComponent(
            color = MaterialTheme.colorScheme.error,
            description = "Report Location",
            icon = Icons.Default.LocationOn,
            x = 80.dp,
            y = 120.dp,
        )
        MarkerComponent(
            color = MaterialTheme.colorScheme.primary,
            description = "Report Location",
            icon = Icons.Default.LocationOn,
            x = 240.dp,
            y = 220.dp,
        )
    }
}
