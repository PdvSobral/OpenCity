package com.example.opencity.models

data class Report(
    val id: Int,
    val title: String,
    val category: String,
    val description: String,
    val address: String,
    val status: ReportStatus,
    val date: String
)

enum class ReportStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED
}
