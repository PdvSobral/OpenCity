package com.example.opencity.models

import androidx.annotation.DrawableRes

data class LanguageOption(
    val name: String,
    @DrawableRes val flagResId: Int?,
    val languageCode: String? // null means System Default
)
