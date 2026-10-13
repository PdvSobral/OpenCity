package com.example.opencity.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Declare this once at top level so the app uses one DataStore instance.
private val Context.settingsDataStore by preferencesDataStore(name = "settings")

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}

data class AppSettings(
    val serverAddress: String = "",
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val notificationsEnabled: Boolean = false,
    val hapticsEnabled: Boolean = false
)

private object SettingsKeys {
    val SERVER_ADDRESS = stringPreferencesKey("server_address")
    val THEME_MODE = stringPreferencesKey("theme_mode")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    val HAPTICS_ENABLED = booleanPreferencesKey("haptics_enabled")
}

class SettingsRepository(private val context: Context) {

    val settings: Flow<AppSettings> = context.settingsDataStore.data.map { prefs ->
        val savedTheme = prefs[SettingsKeys.THEME_MODE]

        AppSettings(
            // Use SYSTEM if the setting is missing or contains an unrecognized value.
            serverAddress = prefs[SettingsKeys.SERVER_ADDRESS] ?: "",
            themeMode = ThemeMode.entries.firstOrNull { it.name == savedTheme } ?: ThemeMode.SYSTEM,
            notificationsEnabled = prefs[SettingsKeys.NOTIFICATIONS_ENABLED] ?: true,
            hapticsEnabled = prefs[SettingsKeys.HAPTICS_ENABLED] ?: true
        )
    }

    suspend fun setServerAddress(addr: String) {
        context.settingsDataStore.edit {
            prefs -> prefs[SettingsKeys.SERVER_ADDRESS] = addr
        }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.settingsDataStore.edit {
            prefs -> prefs[SettingsKeys.THEME_MODE] = mode.name
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.settingsDataStore.edit {
            prefs -> prefs[SettingsKeys.NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun setHapticsEnabled(enabled: Boolean) {
        context.settingsDataStore.edit {
            prefs -> prefs[SettingsKeys.HAPTICS_ENABLED] = enabled
        }
    }
}
