package com.example.opencity.app_navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import com.example.opencity.R
import com.example.opencity.app_navigation.TopLevelRoute


data object AccountNavRoute : TopLevelRoute {
    override val icon = Icons.Outlined.AccountCircle
    override val labelResourceId = R.string.bottom_bar_item_account
}
