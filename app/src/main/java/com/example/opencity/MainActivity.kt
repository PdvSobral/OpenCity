package com.example.opencity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.opencity.models.enums.DarkModeType
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

            OpenCityTheme(
                darkTheme = when (settings.themeMode) {
                    DarkModeType.DARK_MODE -> true
                    DarkModeType.LIGHT_MODE -> false
                    DarkModeType.AUTO -> isSystemInDarkTheme()
                }
            ) {
                ServerAddressGate { _ -> OpenCityApp(settingsRepo = settingsRepository)
                }
            }
        }
    }
}
