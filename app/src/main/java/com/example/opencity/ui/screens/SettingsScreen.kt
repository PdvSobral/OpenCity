package com.example.opencity.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.opencity.ui.components.settings.SettingsAction
import com.example.opencity.ui.components.settings.SettingsDivider
import com.example.opencity.ui.components.settings.SettingsSection
import com.example.opencity.ui.components.settings.SettingsSwitch
import com.example.opencity.ui.components.settings.SettingsSingleFromMultiple
import com.example.opencity.ui.components.settings.SettingsOption


enum class MapOrientation {
    NORTH_UP,
    MANUAL_ROTATION,
    COMPASS
}

enum class DarkModeType {
    LIGHT_MODE,
    DARK_MODE,
    AUTO
}

@Composable
fun SettingsScreen(
    isLoggedIn: Boolean = false,
    onManageMarkers: () -> Unit = {},
    onOfflineMaps: () -> Unit = {},
    onAccountSettings: () -> Unit = {},
    onContributionHistory: () -> Unit = {},
    onServerConfiguration: () -> Unit = {},
    onExportData: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var darkModeType by rememberSaveable { mutableStateOf(DarkModeType.AUTO) }
    var animationsEnabled by rememberSaveable { mutableStateOf(true) }

    var nearbyNotifications by rememberSaveable { mutableStateOf(true) }
    var reportUpdates by rememberSaveable { mutableStateOf(true) }
    var commentNotifications by rememberSaveable { mutableStateOf(true) }
    var highSeverityNotifications by rememberSaveable { mutableStateOf(false) }

    var mapOrientation by rememberSaveable { mutableStateOf(MapOrientation.NORTH_UP) }

    var heatmapEnabled by rememberSaveable { mutableStateOf(false) }
    var pathTracingEnabled by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Text(
            text = "Settings",
            modifier = Modifier.padding(top = 20.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Customize your OpenCity experience",
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        LazyColumn(
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SettingsSection(title = "Appearance") {
                    SettingsSingleFromMultiple(
                        title = "Interface mode",
                        description = "Choose the appearance style of the app",
                        options = listOf(
                            SettingsOption(
                                value = DarkModeType.AUTO,
                                title = "Automatic",
                                description = "Follow system's mode"
                            ),
                            SettingsOption(
                                value = DarkModeType.LIGHT_MODE,
                                title = "Light mode",
                                description = "Always light mode"
                            ),
                            SettingsOption(
                                value = DarkModeType.DARK_MODE,
                                title = "Dark mode",
                                description = "Always dark mode"
                            )
                        ),
                        selectedValue = darkModeType,
                        onValueSelected = { selected -> darkModeType = selected }
                    )

                    SettingsDivider()

                    SettingsSwitch(
                        title = "Animations",
                        description = "Enable transitions and interface animations.\nMight decrease performance.",
                        checked = animationsEnabled,
                        onCheckedChange = { animationsEnabled = it }
                    )
                }
            }

            item {
                SettingsSection(title = "Notifications") {
                    SettingsSwitch(
                        title = "Nearby reports",
                        description = "Notify me about reports near my location",
                        checked = nearbyNotifications,
                        onCheckedChange = { nearbyNotifications = it }
                    )

                    SettingsDivider()

                    SettingsSwitch(
                        title = "Report updates",
                        description = "Notify me when a report changes status",
                        checked = reportUpdates,
                        onCheckedChange = { reportUpdates = it }
                    )

                    SettingsDivider()

                    SettingsSwitch(
                        title = "Comments",
                        description = "Notify me when someone comments on my reports",
                        checked = commentNotifications,
                        onCheckedChange = { commentNotifications = it }
                    )

                    SettingsDivider()

                    SettingsSwitch(
                        title = "High-severity reports",
                        description = "Notify me about urgent problems in my area",
                        checked = highSeverityNotifications,
                        onCheckedChange = { highSeverityNotifications = it }
                    )
                }
            }

            item {
                SettingsSection(title = "Map") {
                    SettingsSingleFromMultiple(
                        title = "Orientation",
                        description = "Choose how the map should rotate",
                        options = listOf(
                            SettingsOption(
                                value = MapOrientation.NORTH_UP,
                                title = "North up",
                                description = "Keep north at the top of the map"
                            ),
                            SettingsOption(
                                value = MapOrientation.MANUAL_ROTATION,
                                title = "Manual rotation",
                                description = "Allow for free manual rotattion of the map"
                            ),
                            SettingsOption(
                                value = MapOrientation.COMPASS,
                                title = "Compass",
                                description = "Rotate the map according to the device's internal compass"
                            )
                        ),
                        selectedValue = mapOrientation,
                        onValueSelected = { selected -> mapOrientation = selected }
                    )

                    SettingsDivider()

                    SettingsSwitch(
                        title = "Heatmap",
                        description = "Show areas with a high concentration of reports",
                        checked = heatmapEnabled,
                        onCheckedChange = { heatmapEnabled = it }
                    )

                    SettingsDivider()

                    SettingsSwitch(
                        title = "Path tracing",
                        description = "Show paths and routes on the map",
                        checked = pathTracingEnabled,
                        onCheckedChange = { pathTracingEnabled = it }
                    )

                    SettingsDivider()

                    SettingsAction(
                        title = "Offline maps",
                        description = "Download and manage offline map areas",
                        buttonText = "Manage",
                        onClick = onOfflineMaps
                    )

                    SettingsAction(
                        title = "Custom markers",
                        description = "Create and manage your map markers",
                        buttonText = "Manage",
                        onClick = onManageMarkers
                    )
                }
            }

            item {
                SettingsSection(title = "Account") {
                    if (isLoggedIn) {
                        SettingsAction(
                            title = "Account settings",
                            description = "Manage your profile and login information",
                            buttonText = "Open",
                            onClick = onAccountSettings
                        )

                        SettingsDivider()

                        SettingsAction(
                            title = "Contribution history",
                            description = "View your reports, comments, and points",
                            buttonText = "View",
                            onClick = onContributionHistory
                        )

                        SettingsDivider()

                        SettingsAction(
                            title = "Export contributions",
                            description = "Export your reports and activity as CSV or JSON",
                            buttonText = "Export",
                            onClick = onExportData
                        )

                        SettingsDivider()

                        TextButton(
                            onClick = onLogout,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = "Log out",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    } else {
                        ListItem(
                            headlineContent = {
                                Text("Anonymous access")
                            },
                            supportingContent = {
                                Text(
                                    "You can view public reports, but account features are unavailable."
                                )
                            }
                        )

                        Button(
                            onClick = onAccountSettings,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Create an account")
                        }
                    }
                }
            }

            item {
                SettingsSection(title = "Server") {
                    SettingsAction(
                        title = "Server configuration",
                        description = "Change the OpenCity server connection",
                        buttonText = "Configure",
                        onClick = onServerConfiguration
                    )
                }
            }

            item {
                SettingsSection(title = "Privacy and data") {
                    SettingsAction(
                        title = "Privacy and data management",
                        description = "Manage stored data and account privacy options",
                        buttonText = "Open",
                        onClick = onAccountSettings
                    )
                }
            }
        }
    }
}
