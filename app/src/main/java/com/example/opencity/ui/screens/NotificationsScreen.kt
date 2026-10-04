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
import com.example.opencity.models.Message
import com.example.opencity.ui.components.MessageCard

@Composable
fun NotificationsScreen() {
    val messages = listOf(
        Message(
            id = 1,
            title = "Report updated",
            body = "Your report about the broken streetlight is now being reviewed.",
            date = "Today",
            author = "OpenCity",
        ),
        Message(
            id = 2,
            title = "Report resolved",
            body = "The overflowing trash bin report has been resolved.",
            date = "Yesterday",
            isRead = true,
            author = "OpenCity"
        ),
        Message(
            id = 3,
            title = "Welcome to OpenCity",
            body = "Help improve your city by reporting local problems.",
            date = "September 28",
            isRead = true,
            author = "OpenCity"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Notifications",
            modifier = Modifier.padding(top = 20.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Updates about your activity",
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) { message ->
                MessageCard(message = message)
            }
        }
    }
}
