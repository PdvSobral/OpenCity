package com.example.opencity.storage

import android.content.Context
import com.example.opencity.R
import com.example.opencity.models.AppLanguage

interface LanguageRepositoryInterface {
    suspend fun getLanguages(): List<AppLanguage>
}
class LanguageRepository : LanguageRepositoryInterface {
    override suspend fun getLanguages(): List<AppLanguage> = listOf(
        AppLanguage(R.string.language_system_default, null, null),
        AppLanguage(R.string.language_english, R.drawable.flag_en, "en"),
        AppLanguage(R.string.language_portuguese, R.drawable.flag_pt_pt, "pt-PT"),
    )
}
