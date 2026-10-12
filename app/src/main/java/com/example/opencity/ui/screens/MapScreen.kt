package com.example.opencity.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.opencity.ui.components.TopBar

@Composable
fun MapScreen(onCreateReport: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(all = 0.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(all = 0.dp)) {
            TopBar("OpenCity", "What is happening in your city?")
        }
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth()) {
            Text(
                text = "Map preview",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            // Example map markers
            Box(
                modifier = Modifier
                    .padding(start = 80.dp, top = 120.dp)
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Report location",
                    tint = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 240.dp, top = 220.dp)
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Report location",
                    tint = Color.White
                )
            }
            Column(
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FloatingActionButton(
                    onClick = {},
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Zoom in"
                    )
                }
                FloatingActionButton(
                    onClick = {},
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Zoom out"
                    )
                }
                // TODO: Move the button up, probably a secondary column on top, with a compass
                OutlinedButton(
                    modifier = Modifier.size(height = 40.dp, width = 122.dp),
                    onClick = onCreateReport
                ) {
                    Icon(
                        imageVector = Icons.Default.Report,
                        contentDescription = "Create report"
                    )
                    Text(
                        text = "Report",
                        modifier = Modifier.padding(all = 0.dp)
                    )
                }
            }
        }
    }
}
