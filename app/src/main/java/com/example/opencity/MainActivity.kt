package com.example.opencity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.opencity.models.DarkModeType
import com.example.opencity.storage.AppSettings
import com.example.opencity.ui.screens.ServerAddressGate
import com.example.opencity.ui.theme.OpenCityTheme
import com.example.opencity.storage.SettingsRepository


class MainActivity : AppCompatActivity() {
    private val settingsRepository by lazy { SettingsRepository(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settings by settingsRepository.settings.collectAsStateWithLifecycle(initialValue = AppSettings())
            val darkTheme = when (settings.themeMode) {
                DarkModeType.DARK_MODE -> true
                DarkModeType.LIGHT_MODE -> false
                DarkModeType.AUTO -> isSystemInDarkTheme()
            }
            OpenCityTheme(darkTheme = darkTheme) {
                ServerAddressGate { serverAddress ->
                    OpenCityApp(serverAddress = serverAddress)
                }
            }
        }
    }
}
