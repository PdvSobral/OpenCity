package com.example.opencity.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AccountScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Account",
            modifier = Modifier.padding(top = 20.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Manage your OpenCity account",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        ListItem(
            headlineContent = {
                Text("Alex Morgan")
            },
            supportingContent = {
                Text("alex.morgan@example.com")
            },
            leadingContent = {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Profile"
                )
            }
        )

        HorizontalDivider()

        ListItem(
            headlineContent = {
                Text("Email notifications")
            },
            supportingContent = {
                Text("Receive updates about your reports")
            },
            leadingContent = {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "Email notifications"
                )
            }
        )
    }
}
