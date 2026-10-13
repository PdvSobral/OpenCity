package com.example.opencity.models

import androidx.appcompat.app.AppCompatDelegate

enum class DarkModeType {
    LIGHT_MODE,
    DARK_MODE,
    AUTO
}

fun DarkModeType.toAppCompatNightMode(): Int = when (this) {
    DarkModeType.DARK_MODE -> AppCompatDelegate.MODE_NIGHT_YES
    DarkModeType.LIGHT_MODE -> AppCompatDelegate.MODE_NIGHT_NO
    DarkModeType.AUTO -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
}
