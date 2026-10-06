package com.example.opencity.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun BottomBar(
    currentRoute: String?,
    onItemSelected: (String) -> Unit
) {
    val items = listOf(
        BottomBarItem("map", "Map", Icons.Outlined.Map),
        BottomBarItem("reports", "Reports", Icons.AutoMirrored.Outlined.Assignment),
        BottomBarItem("notifications", "Notifications", Icons.Outlined.Notifications),
        BottomBarItem("account", "Account", Icons.Outlined.AccountCircle),
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute?.contains(item.route) == true ,
                onClick = { onItemSelected(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}
