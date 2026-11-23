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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opencity.models.enums.DarkModeType
import com.example.opencity.storage.OpenCityApplication
import com.example.opencity.ui.screens.ServerAddressGate
import com.example.opencity.ui.theme.OpenCityTheme
import com.example.opencity.ui.view_models.MainViewModel
import com.example.opencity.ui.view_models.ui_states.MainViewUiState


class MainActivity : AppCompatActivity() {
    private val appContainer get() = (application as OpenCityApplication).container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = viewModel { MainViewModel(appContainer.settingsRepository, appContainer.languageRepository) }
            val uiState by viewModel.uiState.collectAsStateWithLifecycle();
            val theme by viewModel.theme.collectAsStateWithLifecycle();

            OpenCityTheme(
                darkTheme = when (theme) {
                    DarkModeType.DARK_MODE -> true
                    DarkModeType.LIGHT_MODE -> false
                    DarkModeType.AUTO -> isSystemInDarkTheme()
                }
            ) {
                when (uiState) {
                    is MainViewUiState.Loading -> {
                        // TODO: make the loading screen
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                    }
                    is MainViewUiState.ServerAddressNeeded -> {
                        ServerAddressGate(viewModel)
                    }
                    is MainViewUiState.Success -> {
                        OpenCityApp(settingsRepo = appContainer.settingsRepository)
                    }
                    is MainViewUiState.Error -> TODO()
                    else -> throw Error("INVALID MainViewUiState DETECTED!!")
                }
            }
        }
    }
}
