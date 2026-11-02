package com.example.opencity

import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.opencity.ui.screens.SettingsScreen
import com.example.opencity.ui.screens.AccountScreen
import com.example.opencity.ui.screens.MapScreen
import com.example.opencity.ui.screens.NotificationsScreen
import com.example.opencity.ui.screens.ReportsScreen
import com.example.opencity.storage.SettingsRepository
import kotlin.collections.remove

private sealed interface TopLevelRoute {
    val icon: ImageVector
    val labelResourceId: Int
    val route: String
}
private data object Map : TopLevelRoute {
    override val icon = Icons.Outlined.Map
    override val labelResourceId = R.string.bottom_bar_item_map
    override val route = "map"
}
private data object Reports : TopLevelRoute {
    override val icon = Icons.AutoMirrored.Outlined.Assignment
    override val labelResourceId = R.string.bottom_bar_item_report
    override val route = "reports"
}
private data object Report
private data object Notifications : TopLevelRoute {
    override val icon = Icons.Outlined.Notifications
    override val labelResourceId = R.string.bottom_bar_item_notifications
    override val route = "notifications"
}
private data object Account : TopLevelRoute {
    override val icon = Icons.Outlined.AccountCircle
    override val labelResourceId = R.string.bottom_bar_item_account
    override val route = "account"
}


private val TOP_LEVEL_ROUTES : List<TopLevelRoute> = listOf(Map, Reports, Notifications, Account)


@Composable
fun OpenCityApp(
    navController: NavHostController = rememberNavController(),
    settingsRepo: SettingsRepository
) {
    //val navBackStackEntry by navController.currentBackStackEntryAsState()
    //val currentDestination = navBackStackEntry?.destination
    //val currentRoute = currentDestination?.route

    val topLevelBackStack = remember { TopLevelBackStack<Any>(Map) }

    Column (modifier = Modifier.fillMaxSize()){
        Scaffold(
            bottomBar = {
                NavigationBar {
                    TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
                        val isSelected = topLevelRoute == topLevelBackStack.topLevelKey
                        NavigationBarItem(
                            //selected = currentRoute?.contains(item.route) == true ,
                            selected = isSelected,
                            //onClick = { onItemSelected(item.route) },
                            onClick = {
                                topLevelBackStack.addTopLevel(topLevelRoute)
                            },
                            icon = {
                                Icon(
                                    imageVector = topLevelRoute.icon,
                                    contentDescription = stringResource(topLevelRoute.labelResourceId)
                                )
                            },
                            label = { Text(stringResource(topLevelRoute.labelResourceId)) }
                        )
                    }
                }
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
                composable("account") {
                    AccountScreen(/*account = Account(
                        id = 1,
                        username = "alex",
                        displayName = "Alex Smith",
                        points = 125,
                        role = AccountRole.USER,
                        createdAt = "2026"
                    ),*/
                    onAppSettings = { navController.navigate("account_appsettings")})
                }
                composable("account_appsettings") {
                    SettingsScreen(
                        onReturn = { navController.popBackStack() },
                        settingsRepo = settingsRepo
                    )
                }
            }
        }
    }
}


class TopLevelBackStack<T: Any>(startKey: T) {
    // Maintain a stack for each top level route
    private var topLevelStacks : LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(
        startKey to mutableStateListOf(startKey)
    )

    // Expose the current top level route for consumers
    var topLevelKey by mutableStateOf(startKey)
        private set

    // Expose the back stack so it can be rendered by the NavDisplay
    val backStack = mutableStateListOf(startKey)

    private fun updateBackStack() =
        backStack.apply {
            clear()
            addAll(topLevelStacks.flatMap { it.value })
        }

    fun addTopLevel(key: T){
        // If the top level doesn't exist, add it
        if (topLevelStacks[key] == null){
            topLevelStacks.put(key, mutableStateListOf(key))
        } else {
            // Otherwise just move it to the end of the stacks
            topLevelStacks.apply {
                remove(key)?.let {
                    put(key, it)
                }
            }
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T){
        topLevelStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast(){
        val removedKey = topLevelStacks[topLevelKey]?.removeLastOrNull()
        // If the removed key was a top level key, remove the associated top level stack
        topLevelStacks.remove(removedKey)
        topLevelKey = topLevelStacks.keys.last()
        updateBackStack()
    }
}
