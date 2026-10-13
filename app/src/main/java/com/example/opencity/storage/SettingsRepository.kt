package com.example.opencity.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.opencity.models.enums.DarkModeType
import com.example.opencity.models.enums.MapOrientationMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Declare this once at top level so the app uses one DataStore instance.
private val Context.settingsDataStore by preferencesDataStore(name = "settings")


data class AppSettings(
    val serverAddress: String = "",
    val themeMode: DarkModeType = DarkModeType.AUTO,
    val notificationsEnabled: Boolean = false,
    val mapOrientationMode: MapOrientationMode = MapOrientationMode.NORTH_UP
)

private object SettingsKeys {
    val MAP_ORIENTATION_MODE = stringPreferencesKey("map_orientation_mode")
    val SERVER_ADDRESS = stringPreferencesKey("server_address")
    val THEME_MODE = stringPreferencesKey("theme_mode")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
}

class SettingsRepository(private val context: Context) {

    val settings: Flow<AppSettings> = context.settingsDataStore.data.map { prefs ->
        AppSettings(
            serverAddress = prefs[SettingsKeys.SERVER_ADDRESS] ?: "",
            themeMode = DarkModeType.entries.firstOrNull { it.name == prefs[SettingsKeys.THEME_MODE] } ?: DarkModeType.AUTO,
            mapOrientationMode = MapOrientationMode.entries.firstOrNull { it.name == prefs[SettingsKeys.MAP_ORIENTATION_MODE] } ?: MapOrientationMode.NORTH_UP,
            notificationsEnabled = prefs[SettingsKeys.NOTIFICATIONS_ENABLED] ?: true,
        )
    }

    suspend fun setServerAddress(addr: String) {
        context.settingsDataStore.edit {
            prefs -> prefs[SettingsKeys.SERVER_ADDRESS] = addr
        }
    }

    suspend fun setThemeMode(mode: DarkModeType) {
        context.settingsDataStore.edit {
            prefs -> prefs[SettingsKeys.THEME_MODE] = mode.name
        }
    }

    suspend fun setMapOrientationMode(mode: MapOrientationMode) {
        context.settingsDataStore.edit {
                prefs -> prefs[SettingsKeys.MAP_ORIENTATION_MODE] = mode.name
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[SettingsKeys.NOTIFICATIONS_ENABLED] = enabled
        }
    }
}
