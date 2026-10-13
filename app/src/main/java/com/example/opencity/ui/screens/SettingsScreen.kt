package com.example.opencity.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.opencity.R
import com.example.opencity.ui.components.TopBar
import com.example.opencity.ui.components.settings.SettingsAction
import com.example.opencity.ui.components.settings.SettingsDivider
import com.example.opencity.ui.components.settings.SettingsSection
import com.example.opencity.ui.components.settings.SettingsSwitch
import com.example.opencity.ui.components.settings.SettingsSingleFromMultiple
import com.example.opencity.ui.components.settings.SettingsOption
import com.example.opencity.models.enums.DarkModeType
import com.example.opencity.models.enums.MapOrientationMode
import com.example.opencity.storage.AppSettings
import com.example.opencity.storage.SettingsRepository
import kotlinx.coroutines.launch


@Composable
fun SettingsScreen(
    onManageMarkers: () -> Unit = {},
    onOfflineMaps: () -> Unit = {},
    onAccountSettings: () -> Unit = {},
    onReturn: () -> Unit = {}
) {
    val context = LocalContext.current
    val repository = remember(context.applicationContext) { SettingsRepository(context.applicationContext) }
    val scope = rememberCoroutineScope()
    val settings by produceState<AppSettings?>(initialValue = null, key1 = repository) { repository.settings.collect { value = it } }
    val currentSettings = settings
    if (currentSettings == null) {
        // Loading... No one can escape the damn loading screen... Muahahahaha! (*mental sanity left chat*)
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }
    val darkModeType = currentSettings.themeMode
    fun saveDarkModeType(mode: DarkModeType) { scope.launch { repository.setThemeMode(mode) } }

    var animationsEnabled by rememberSaveable { mutableStateOf(true) }

    var nearbyNotifications by rememberSaveable { mutableStateOf(true) }
    var reportUpdates by rememberSaveable { mutableStateOf(true) }
    var commentNotifications by rememberSaveable { mutableStateOf(true) }
    var highSeverityNotifications by rememberSaveable { mutableStateOf(false) }

    val mapOrientation = currentSettings.mapOrientationMode
    fun saveMapOrientationMode(mode: MapOrientationMode) { scope.launch { repository.setMapOrientationMode(mode) } }

    var heatmapEnabled by rememberSaveable { mutableStateOf(false) }
    var pathTracingEnabled by rememberSaveable { mutableStateOf(false) }

    fun onServerConfiguration() {
        // TODO: make confirmation that the user will be logged out. local markers will not be lost.
        scope.launch { repository.setServerAddress("") }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(tittle = stringResource(R.string.settings_screen_top_bar_title), stringResource(R.string.settings_screen_top_bar_description), true, onReturn)
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    SettingsSection(title = stringResource(R.string.settings_screen_appearance_section)) {
                        SettingsSingleFromMultiple(
                            title = stringResource(R.string.settings_screen_interface_mode_title),
                            description = stringResource(R.string.settings_screen_interface_mode_description),
                            options = listOf(
                                SettingsOption(
                                    value = DarkModeType.AUTO,
                                    title = stringResource(R.string.settings_screen_interface_automatic_title),
                                    description = stringResource(R.string.settings_screen_interface_automatic_description)
                                ),
                                SettingsOption(
                                    value = DarkModeType.LIGHT_MODE,
                                    title = stringResource(R.string.settings_screen_interface_light_mode_title),
                                    description = stringResource(R.string.settings_screen_interface_light_mode_description)
                                ),
                                SettingsOption(
                                    value = DarkModeType.DARK_MODE,
                                    title = stringResource(R.string.settings_screen_interface_dark_mode_title),
                                    description = stringResource(R.string.settings_screen_interface_dark_mode_description))
                            ),
                            selectedValue = darkModeType,
                            onValueSelected = { selected -> saveDarkModeType(selected)}
                        )

                        SettingsDivider()

                        SettingsSwitch(
                            title = stringResource(R.string.settings_screen_animation_switch_title),
                            description = stringResource(R.string.settings_screen_animation_switch_description), //nao sei como meter o \n
                            checked = animationsEnabled,
                            onCheckedChange = { animationsEnabled = it }
                        )
                    }
                }

                item {
                    SettingsSection(title = stringResource(R.string.settings_screen_notifications_section)) {
                        SettingsSwitch(
                            title = stringResource(R.string.settings_screen_notifications_section_nearby_reports_switch_title),
                            description = stringResource(R.string.settings_screen_notifications_section_nearby_reports_switch_description),
                            checked = nearbyNotifications,
                            onCheckedChange = { nearbyNotifications = it }
                        )

                        SettingsDivider()

                        SettingsSwitch(
                            title = stringResource(R.string.settings_screen_notifications_section_report_updates_switch_title),
                            description = stringResource(R.string.settings_screen_notifications_section_report_updates_switch_description),
                            checked = reportUpdates,
                            onCheckedChange = { reportUpdates = it }
                        )

                        SettingsDivider()

                        SettingsSwitch(
                            title = stringResource(R.string.settings_screen_notifications_section_comments_switch_title),
                            description = stringResource(R.string.settings_screen_notifications_section_comments_switch_description),
                            checked = commentNotifications,
                            onCheckedChange = { commentNotifications = it }
                        )

                        SettingsDivider()

                        SettingsSwitch(
                            title = stringResource(R.string.settings_screen_notifications_section_high_severity_reports_switch_title),
                            description = stringResource(R.string.settings_screen_notifications_section_high_severity_reports_switch_description),
                            checked = highSeverityNotifications,
                            onCheckedChange = { highSeverityNotifications = it }
                        )
                    }
                }

                item {
                    SettingsSection(title = stringResource(R.string.settings_screen_map_section)) {
                        SettingsSingleFromMultiple(
                            title = "Orientation",
                            description = "Choose how the map should rotate",
                            options = listOf(
                                SettingsOption(
                                    value = MapOrientationMode.NORTH_UP,
                                    title = "North up",
                                    description = "Keep north at the top of the map"
                                ),
                                SettingsOption(
                                    value = MapOrientationMode.MANUAL_ROTATION,
                                    title = "Manual rotation",
                                    description = "Allow for free manual rotation of the map"
                                ),
                                SettingsOption(
                                    value = MapOrientationMode.COMPASS,
                                    title = "Compass",
                                    description = "Rotate the map according to the device's internal compass"
                                )
                            ),
                            selectedValue = mapOrientation,
                            onValueSelected = { selected -> saveMapOrientationMode(selected) }
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
                    SettingsSection(title = "Server") {
                        SettingsAction(
                            title = "Change Server",
                            description = "Change the OpenCity server connection",
                            buttonText = "Configure",
                            onClick = { onServerConfiguration() }
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
}
