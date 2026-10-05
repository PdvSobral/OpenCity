package com.example.opencity

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.opencity.ui.components.BottomBar
import com.example.opencity.ui.screens.AccountScreen
import com.example.opencity.ui.screens.MapScreen
import com.example.opencity.ui.screens.NotificationsScreen
import com.example.opencity.ui.screens.ReportsScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import com.example.opencity.models.Account
import com.example.opencity.models.AccountRole
import com.example.opencity.ui.screens.SettingsScreen


@Composable
fun OpenCityApp(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    Column (modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                BottomBar(
                    currentRoute = currentRoute,
                    onItemSelected = { route ->
                        navController.navigate(route) {
                            popUpTo("map") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "map",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("map") {MapScreen(onCreateReport = {})} // Add report creation navigation later.
                composable("reports") { ReportsScreen() }
                composable("notifications") { NotificationsScreen() }
                composable("account") { AccountScreen(account = Account(
                    id = 1,
                    username = "alex",
                    displayName = "Alex Smith",
                    points = 125,
                    role = AccountRole.USER,
                    createdAt = "2026"
                )
                ) }
                composable("settings") { SettingsScreen() }
            }
        }
    }
}
