package com.example.opencity.models

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class Report(
    val id: Int,
    val title: String,
    val category: String,
    val description: String,
    val address: String,
    val status: ReportStatus,
    val date: String
)

@Suppress("unused") // The enum, even if not used, has to have all the states
enum class ReportStatus {
    REPORTED,       // Default, when reported
    CONFIRMED,      // Other users confirmed / admins aproved it
    IN_RESOLUTION,  // There is currently an effort being done to solve it

    DUPLICATE,      // Closed, there is already one about it
    DENIED,         // Maybe from Spam, or simply fake
    RESOLVED        // It is considered a case closed and solved
}

// TODO: Finish Mapping
fun ReportStatus.toStringValue(): String = when (this) {
   ReportStatus.IN_RESOLUTION -> "In resolution"
   ReportStatus.REPORTED -> "In progress"
   ReportStatus.RESOLVED -> "Resolved"
   else -> {"UNDEFINED"}
}

// TODO: Finish Mapping
@Composable
fun ReportStatus.toColourValue(): Color = when (this) {
    ReportStatus.REPORTED -> MaterialTheme.colorScheme.error
    ReportStatus.IN_RESOLUTION -> MaterialTheme.colorScheme.primary
    ReportStatus.RESOLVED -> MaterialTheme.colorScheme.tertiary
    else -> {MaterialTheme.colorScheme.error}
}
