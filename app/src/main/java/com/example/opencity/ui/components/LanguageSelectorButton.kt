package com.example.opencity.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.core.os.LocaleListCompat
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.res.painterResource
import com.example.opencity.models.LanguageOption


@Composable
fun LanguageSelectorButton(
    languages: List<LanguageOption>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    // Retrieve the currently configured app locale.
    // If no app-specific locale is set, LocaleListCompat.get(0) returns null,
    // so we safely fall back to a default (e.g., "en").
    val currentLocaleTag = AppCompatDelegate.getApplicationLocales().get(0)?.toLanguageTag()
    // Compare to options. if not found, fallback to first, which should be system default.
    // TODO: test what happens if the device is in a non-translated language and system language is selected, has to fallback to english
    val selectedLanguage = languages.firstOrNull { option ->
        val languageCode = option.languageCode
        if (currentLocaleTag.isNullOrBlank()) { languageCode == null }
        else {
            languageCode != null && (
                    currentLocaleTag.equals(
                            languageCode, ignoreCase = true) ||
                            currentLocaleTag.startsWith("$languageCode-", ignoreCase = true) ||
                            languageCode.startsWith("$currentLocaleTag-", ignoreCase = true)
                    )
        }
    } ?: languages.firstOrNull()
    Box(modifier = modifier) {
        Button(onClick = { expanded = true }) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                selectedLanguage?.flagResId?.let { flag ->
                    Image(
                        painter = painterResource(flag),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                } ?: Text("🌐") // if no flag, put a globe
                Text(selectedLanguage?.name ?: "Language")
            }
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            languages.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            option.flagResId?.let { flag ->
                                Image(
                                    painter = painterResource(flag),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            } ?: Text("🌐")
                            Text(option.name)
                        }
                    },
                    onClick = {
                        expanded = false
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.forLanguageTags(option.languageCode.orEmpty())
                        )
                    }
                )
            }
        }
    }
}
