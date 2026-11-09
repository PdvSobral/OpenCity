package com.example.opencity.app_navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import com.example.opencity.R
import com.example.opencity.app_navigation.TopLevelRoute


data object NotificationsNavRoute : TopLevelRoute {
    override val icon = Icons.Outlined.Notifications
    override val labelResourceId = R.string.bottom_bar_item_notifications
}
