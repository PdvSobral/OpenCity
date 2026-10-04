package com.example.opencity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.opencity.models.Report
import com.example.opencity.models.ReportStatus

@Composable
fun ReportCard(
    report: Report,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val statusText = when (report.status) {
        ReportStatus.OPEN -> "Open"
        ReportStatus.IN_PROGRESS -> "In progress"
        ReportStatus.RESOLVED -> "Resolved"
    }

    val statusColor = when (report.status) {
        ReportStatus.OPEN -> MaterialTheme.colorScheme.error
        ReportStatus.IN_PROGRESS -> MaterialTheme.colorScheme.primary
        ReportStatus.RESOLVED -> MaterialTheme.colorScheme.tertiary
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = report.title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = report.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = statusText,
                    color = statusColor,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Text(
                text = report.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Location"
                )

                Text(
                    text = report.address,
                    modifier = Modifier.padding(start = 4.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = report.date,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
