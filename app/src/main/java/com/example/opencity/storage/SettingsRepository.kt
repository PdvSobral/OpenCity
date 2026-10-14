package com.example.opencity.storage

import android.content.Context
import androidx.datastore.preferences.core.Preferences
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
    val notificationsEnabled: Boolean = false,
    val themeMode: DarkModeType = DarkModeType.AUTO,
    val mapOrientationMode: MapOrientationMode = MapOrientationMode.NORTH_UP
)

object SettingsKeys {
    val SERVER_ADDRESS = stringPreferencesKey("server_address")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    val THEME_MODE = stringPreferencesKey("theme_mode")
    val MAP_ORIENTATION_MODE = stringPreferencesKey("map_orientation_mode")
}

class SettingsRepository(private val context: Context) {

    val settings: Flow<AppSettings> = context.settingsDataStore.data.map { prefs ->
        AppSettings(
            serverAddress = prefs[SettingsKeys.SERVER_ADDRESS] ?: "",
            notificationsEnabled = prefs[SettingsKeys.NOTIFICATIONS_ENABLED] ?: false,
            themeMode = DarkModeType.entries.firstOrNull { it.name == prefs[SettingsKeys.THEME_MODE] } ?: DarkModeType.AUTO,
            mapOrientationMode = MapOrientationMode.entries.firstOrNull { it.name == prefs[SettingsKeys.MAP_ORIENTATION_MODE] } ?: MapOrientationMode.NORTH_UP,
        )
    }

    suspend fun <T> setSettingValue(key: Preferences.Key<T>, value: T) {
        context.settingsDataStore.edit { prefs -> prefs[key] = value }
    }
    // Values that are not one of the basic types need overloads
    suspend fun setSettingValue(key: Preferences.Key<String>, value: DarkModeType) {
        context.settingsDataStore.edit { prefs -> prefs[key] = value.name }
    }
    suspend fun setSettingValue(key: Preferences.Key<String>, value: MapOrientationMode) {
        context.settingsDataStore.edit { prefs -> prefs[key] = value.name }
    }
}
