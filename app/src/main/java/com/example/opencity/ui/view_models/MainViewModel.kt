package com.example.opencity.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opencity.models.AppLanguage
import com.example.opencity.models.enums.DarkModeType
import com.example.opencity.storage.AppSettingsInterface
import com.example.opencity.storage.LanguageRepository
import com.example.opencity.storage.LanguageRepositoryInterface
import com.example.opencity.storage.Settings
import com.example.opencity.ui.view_models.ui_states.MainViewUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.IOException


// recebe o repositório
class MainViewModel(
    private val settingsRepo: AppSettingsInterface,
    private val languageRepository: LanguageRepositoryInterface,
) : ViewModel() {
    private val _uiState = MutableStateFlow<MainViewUiState>(MainViewUiState.Loading)
    private val _theme = MutableStateFlow(DarkModeType.AUTO)
    private val _languages = MutableStateFlow<List<AppLanguage>>(listOf())
    val uiState: StateFlow<MainViewUiState> = _uiState.asStateFlow()
    val theme: StateFlow<DarkModeType> = _theme.asStateFlow()

    // carrega os dados uma vez, quando o ViewModel é criado
    init { carregar() }

    private fun carregar() {
        viewModelScope.launch {
            _uiState.value = MainViewUiState.Loading
            settingsRepo.getSettings().catch { e ->
                if (e is IOException) { _uiState.value = MainViewUiState.Error("Error when loading server address and/or language!") }
                else { throw e }
            }.collect { settings ->
                _theme.value = settings.themeMode
                if (settings.serverAddress.takeIf { it.isNotBlank() } == null) { _uiState.value = MainViewUiState.ServerAddressNeeded }
                else { _uiState.value = MainViewUiState.Success }
            }
        }
    }

    fun setServerAddress(address: String) {
        viewModelScope.launch {
            settingsRepo.setSettingValue(Settings.SERVER_ADDRESS, address);
        }
    }
}
