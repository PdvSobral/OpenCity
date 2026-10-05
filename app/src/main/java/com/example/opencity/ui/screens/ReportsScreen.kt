package com.example.opencity.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.opencity.models.Report
import com.example.opencity.models.ReportStatus
import com.example.opencity.ui.components.ReportCard
import com.example.opencity.ui.components.TopBar

@Composable
fun ReportsScreen() {
    val reports = listOf(
        Report(
            id = 1,
            title = "Broken streetlight",
            category = "Public lighting",
            description = "The streetlight has not been working for several days.",
            address = "Main Avenue, 120",
            status = ReportStatus.IN_PROGRESS,
            date = "Today"
        ),
        Report(
            id = 2,
            title = "Pothole on the road",
            category = "Roads",
            description = "Large pothole causing difficulty for cars and bicycles.",
            address = "Central Street, 45",
            status = ReportStatus.OPEN,
            date = "Yesterday"
        ),
        Report(
            id = 3,
            title = "Overflowing trash bin",
            category = "Waste collection",
            description = "The public trash bin is full and needs collection.",
            address = "Liberty Square",
            status = ReportStatus.RESOLVED,
            date = "September 28"
        )
    )

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        TopBar("My reports", "Follow the status of your reports")

        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(reports) { report ->
                ReportCard(report = report)
            }
        }
    }
}
