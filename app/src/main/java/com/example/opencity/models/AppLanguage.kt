package com.example.opencity.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class AppLanguage(
    @StringRes val labelRes: Int,
    @DrawableRes val flagRes: Int?,
    val tag: String?,
)
