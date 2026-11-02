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
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.opencity.R
import com.example.opencity.ui.components.TopBar
import com.example.opencity.ui.components.settings.SettingsAction
import com.example.opencity.ui.components.settings.SettingsSection
import com.example.opencity.storage.AppSettings
import com.example.opencity.storage.SettingsRepository
import kotlinx.coroutines.CoroutineScope


@Composable
fun SecuritySettingsScreen(
    onAccountSettings: () -> Unit = {},
    onReturn: () -> Unit = {},
    settingsRepo: SettingsRepository
) {
    val scope: CoroutineScope = rememberCoroutineScope()
    val settings by produceState<AppSettings?>(initialValue = null, key1 = settingsRepo) { settingsRepo.settings.collect { value = it } }
    val currentSettings = settings
    if (currentSettings == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }
    /*
    val darkModeType = currentSettings.themeMode
    fun saveDarkModeType(mode: DarkModeType) { scope.launch {
        settingsRepo.setSettingValue(
            Settings.THEME_MODE,
            mode
        ); } }
    */
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(tittle = stringResource(R.string.application_settings_screen_top_bar_title), stringResource(R.string.application_settings_screen_top_bar_description), true, onReturn)
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    SettingsSection(title  = stringResource(R.string. settings_screen_privacy_section_title)) {
                        SettingsAction(
                            title = stringResource(R.string. settings_screen_privacy_data_management_section_title),
                            description = stringResource(R.string. settings_screen_privacy_data_management_section_description),
                            buttonText = stringResource(R.string. settings_screen_privacy_data_management_section_button),
                            onClick = onAccountSettings
                        )
                    }
                }
            }
        }
    }
}
