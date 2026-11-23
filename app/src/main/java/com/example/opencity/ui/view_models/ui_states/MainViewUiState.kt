package com.example.opencity.ui.view_models.ui_states

import com.example.opencity.models.enums.DarkModeType

interface MainViewUiState {
    data object Loading : MainViewUiState
    data object Success : MainViewUiState
    data object ServerAddressNeeded : MainViewUiState
    data class Error(val msg: String) : MainViewUiState
}
