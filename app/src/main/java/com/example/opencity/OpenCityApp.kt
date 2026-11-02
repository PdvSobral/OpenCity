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
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
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
}
private data object Map : TopLevelRoute {
    override val icon = Icons.Outlined.Map
    override val labelResourceId = R.string.bottom_bar_item_map
}
private data object Reports : TopLevelRoute {
    override val icon = Icons.AutoMirrored.Outlined.Assignment
    override val labelResourceId = R.string.bottom_bar_item_report
}
private data object ReportScreen {
    val report_id: Int? = null
}
private data object Notifications : TopLevelRoute {
    override val icon = Icons.Outlined.Notifications
    override val labelResourceId = R.string.bottom_bar_item_notifications
}
private data object Account : TopLevelRoute {
    override val icon = Icons.Outlined.AccountCircle
    override val labelResourceId = R.string.bottom_bar_item_account
}
private data object Settings

private val TOP_LEVEL_ROUTES : List<TopLevelRoute> = listOf(Map, Reports, Notifications, Account)


@Composable
fun OpenCityApp(
    settingsRepo: SettingsRepository
) {
    val topLevelBackStack = remember { TopLevelBackStack<Any>(Map) }

    Column (modifier = Modifier.fillMaxSize()){
        Scaffold(
            bottomBar = {
                NavigationBar {
                    TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
                        val isSelected = topLevelRoute == topLevelBackStack.topLevelKey
                        NavigationBarItem(
                            selected = isSelected,
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
            NavDisplay(
                modifier = Modifier.padding(innerPadding),
                backStack = topLevelBackStack.backStack,
                onBack = { topLevelBackStack.removeLast() },
                entryProvider = entryProvider {
                    entry<Map>{
                        MapScreen(onCreateReport = {})
                    }
                    entry<Reports>{
                        ReportsScreen()
                    }
                    entry<Notifications>{
                        NotificationsScreen()
                    }
                    entry<Account>{
                        AccountScreen(
                            /*account = Account(
                                id = 1,
                                username = "alex",
                                displayName = "Alex Smith",
                                points = 125,
                                role = AccountRole.USER,
                                createdAt = "2026"
                            ),*/
                            onAppSettings = { topLevelBackStack.add(Settings)}
                        )
                    }
                    entry<Settings>{
                        SettingsScreen(
                            onReturn = { topLevelBackStack.removeLast() },
                            settingsRepo = settingsRepo
                        )
                    }
                    entry<ReportScreen> {
                        // TODO: make the screen for reports, receives the report uuid.
                        // for now, on the screen, we can put static data form a Report struct,
                        // then we only need to change that (id will pass with it.report_id)
                    }
                },
            )
        }
    }
}


class TopLevelBackStack<T: Any>(startKey: T) {
    // Maintain a stack for each top level route
    private var topLevelStacks : LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(startKey to mutableStateListOf(startKey))

    // Expose the current top level route for consumers
    var topLevelKey by mutableStateOf(startKey)
        private set

    // Expose the back stack so it can be rendered by the NavDisplay
    val backStack = mutableStateListOf(startKey)

    private fun updateBackStack() = backStack.apply { clear(); addAll(topLevelStacks.flatMap { it.value }) }

    fun addTopLevel(key: T){
        // If the top level doesn't exist, add it
        if (topLevelStacks[key] == null){ topLevelStacks[key] = mutableStateListOf(key) }
        // Otherwise just move it to the end of the stacks
        else { topLevelStacks.apply { remove(key)?.let { put(key, it) } } }
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
