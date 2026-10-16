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


data class SettingOption<KeyType, ValueType>(
    val key: Preferences.Key<KeyType>,
    val defaultValue: ValueType,
    val encode: (ValueType) -> KeyType,
    val decode: (KeyType) -> ValueType?
){ fun readFrom(preferences: Preferences): ValueType = preferences[key]?.let(decode) ?: defaultValue}


object Settings {
    val SERVER_ADDRESS = SettingOption(
        key = stringPreferencesKey("server_address"),
        defaultValue = "",
        encode = { it },
        decode = { it }
    )

    val NOTIFICATIONS_ENABLED = SettingOption(
        key = booleanPreferencesKey("notifications_enabled"),
        defaultValue = false,
        encode = { it },
        decode = { it }
    )

    val THEME_MODE = SettingOption(
        key = stringPreferencesKey("theme_mode"),
        defaultValue = DarkModeType.AUTO,
        encode = { it.name },
        decode = { stored -> DarkModeType.entries.firstOrNull { it.name == stored } }
    )

    val MAP_ORIENTATION_MODE = SettingOption(
        key = stringPreferencesKey("map_orientation_mode"),
        defaultValue = MapOrientationMode.NORTH_UP,
        encode = { it.name },
        decode = { stored -> MapOrientationMode.entries.firstOrNull { it.name == stored } }
    )

    val ANIMATIONS = SettingOption(
        key = booleanPreferencesKey("animations_on"),
        defaultValue = true,
        encode = { it },
        decode = { it }
    )
}


data class AppSettings(
    val serverAddress: String = Settings.SERVER_ADDRESS.defaultValue,
    val notificationsEnabled: Boolean = Settings.NOTIFICATIONS_ENABLED.defaultValue,
    val themeMode: DarkModeType = Settings.THEME_MODE.defaultValue,
    val mapOrientationMode: MapOrientationMode = Settings.MAP_ORIENTATION_MODE.defaultValue,
    val animationsOn: Boolean = Settings.ANIMATIONS.defaultValue
)


class SettingsRepository(private val context: Context) {

    val settings: Flow<AppSettings> = context.settingsDataStore.data.map { prefs ->
        AppSettings(
            serverAddress = Settings.SERVER_ADDRESS.readFrom(prefs),
            notificationsEnabled = Settings.NOTIFICATIONS_ENABLED.readFrom(prefs),
            themeMode = Settings.THEME_MODE.readFrom(prefs),
            mapOrientationMode = Settings.MAP_ORIENTATION_MODE.readFrom(prefs),
            animationsOn = Settings.ANIMATIONS.readFrom(prefs)
        )
    }

    suspend fun <K, V> setSettingValue(option: SettingOption<K, V>, value: V) {
        context.settingsDataStore.edit { prefs -> prefs[option.key] = option.encode(value) }
    }
}
