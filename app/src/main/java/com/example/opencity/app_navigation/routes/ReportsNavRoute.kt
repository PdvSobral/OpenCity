package com.example.opencity.app_navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import com.example.opencity.R
import com.example.opencity.app_navigation.TopLevelRoute

data object ReportsNavRoute : TopLevelRoute {
    override val icon = Icons.AutoMirrored.Outlined.Assignment
    override val labelResourceId = R.string.bottom_bar_item_report
}