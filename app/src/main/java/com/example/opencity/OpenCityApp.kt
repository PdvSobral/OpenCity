package com.example.opencity

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.opencity.app_navigation.NavigationStack
import com.example.opencity.app_navigation.TopLevelRoute
import com.example.opencity.app_navigation.routes.AccountNavRoute
import com.example.opencity.app_navigation.routes.AccountNavSubRouteApplicationSettings
import com.example.opencity.app_navigation.routes.AccountNavSubRouteNotificationSettings
import com.example.opencity.app_navigation.routes.AccountNavSubRouteSecuritySettings
import com.example.opencity.app_navigation.routes.MapNavRoute
import com.example.opencity.app_navigation.routes.NotificationsNavRoute
import com.example.opencity.app_navigation.routes.ReportNavSubRouteReportDetail
import com.example.opencity.app_navigation.routes.ReportsNavRoute
import com.example.opencity.storage.AppSettings
import com.example.opencity.storage.SettingsRepository
import com.example.opencity.ui.screens.ApplicationSettingsScreen
import com.example.opencity.ui.screens.AccountScreen
import com.example.opencity.ui.screens.MapScreen
import com.example.opencity.ui.screens.NotificationsScreen
import com.example.opencity.ui.screens.ReportsScreen
import com.example.opencity.ui.screens.NotificationSettingsScreen
import com.example.opencity.ui.screens.ReportDetailScreen
import com.example.opencity.ui.screens.SecuritySettingsScreen


private val TOP_LEVEL_ROUTES : List<TopLevelRoute> = listOf(
    MapNavRoute,
    ReportsNavRoute,
    NotificationsNavRoute,
    AccountNavRoute
)


@Composable
fun OpenCityApp(
    settingsRepo: SettingsRepository
) {
    val settings by produceState<AppSettings?>(initialValue = null, key1 = settingsRepo) { settingsRepo.settings.collect { value = it } }
    val currentSettings = settings
    if (currentSettings == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }

    val navigationStack = remember { NavigationStack<Any>(MapNavRoute) }

    Column (modifier = Modifier.fillMaxSize()){
        Scaffold(
            bottomBar = {
                NavigationBar {
                    TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
                        val isSelected = topLevelRoute == navigationStack.topLevelKey
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navigationStack.addTopLevel(topLevelRoute)
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
                backStack = navigationStack.backStack,
                onBack = { navigationStack.removeLast() },
                entryProvider = entryProvider {
                    entry<MapNavRoute>{
                        MapScreen(onCreateReport = {}, currentSettings.mapOrientationMode)
                    }
                    entry<ReportsNavRoute>{
                        ReportsScreen(
                            onReportClick = { navigationStack.add(ReportNavSubRouteReportDetail(it)) }
                        )
                    }
                    entry<NotificationsNavRoute>{
                        NotificationsScreen()
                    }
                    entry<AccountNavRoute>{
                        AccountScreen(
                            onAppSettings = { navigationStack.add(
                                AccountNavSubRouteApplicationSettings
                            ) },
                            onNotificationSettings = { navigationStack.add(
                                AccountNavSubRouteNotificationSettings
                            ) },
                            onSecuritySettings = { navigationStack.add(
                                AccountNavSubRouteSecuritySettings
                            ) }
                        )
                    }
                    entry<AccountNavSubRouteApplicationSettings>{
                        ApplicationSettingsScreen(
                            onReturn = { navigationStack.removeLast() },
                            settingsRepo = settingsRepo
                        )
                    }
                    entry<AccountNavSubRouteNotificationSettings>{
                        NotificationSettingsScreen(
                            onReturn = { navigationStack.removeLast() },
                            settingsRepo = settingsRepo
                        )
                    }
                    entry<AccountNavSubRouteSecuritySettings>{
                        SecuritySettingsScreen(
                            onReturn = { navigationStack.removeLast() },
                            settingsRepo = settingsRepo
                        )
                    }
                    entry<ReportNavSubRouteReportDetail> {
                        ReportDetailScreen(
                            report_id = it.reportId,
                            onReturn = { navigationStack.removeLast() }
                        )
                    }
                },
            )
        }
    }
}

