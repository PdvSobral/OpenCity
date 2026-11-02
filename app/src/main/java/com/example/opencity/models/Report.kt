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
    RESOLVED,       // It is considered a case closed and solved
    DENIED,         // Maybe from Spam, or simply fake, should also include a message of why

    UNKNOWN         // Used for when the status returned in the API is not recognized
}

fun ReportStatus.toStringValue(): String = when (this) {
    ReportStatus.REPORTED -> "In progress"
    ReportStatus.CONFIRMED -> ""
    ReportStatus.IN_RESOLUTION -> "In resolution"
    ReportStatus.RESOLVED -> "Resolved"
    ReportStatus.DENIED -> ""
    else -> {"UNDEFINED"} // Should never occur, but for safety
}

@Composable
fun ReportStatus.toColourValue(): Color = when (this) {
    ReportStatus.REPORTED -> MaterialTheme.colorScheme.error
    ReportStatus.CONFIRMED -> MaterialTheme.colorScheme.primary
    ReportStatus.IN_RESOLUTION -> MaterialTheme.colorScheme.primary
    ReportStatus.RESOLVED -> MaterialTheme.colorScheme.tertiary
    ReportStatus.DENIED -> MaterialTheme.colorScheme.error
    else -> {MaterialTheme.colorScheme.error}
}
