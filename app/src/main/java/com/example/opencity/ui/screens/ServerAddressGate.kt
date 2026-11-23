package com.example.opencity.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.opencity.R
import com.example.opencity.models.LanguageOption
import com.example.opencity.ui.components.mainview.AddressForm
import com.example.opencity.ui.components.mainview.Instructions
import com.example.opencity.ui.components.mainview.LanguageSelectorButton
import com.example.opencity.ui.view_models.MainViewModel


@Composable
fun ServerAddressGate(
    viewModel: MainViewModel
) {
    var input by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var pendingHttpAddress by remember { mutableStateOf<String?>(null) }

    fun saveAddress(address: String) {
        viewModel.setServerAddress(address)
        pendingHttpAddress = null
    }

    Box(
        modifier = Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.surface
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        LanguageSelectorButton(
            languages = listOf( // TODO: Pull from LanguageRepository
                LanguageOption(stringResource(R.string.language_system_default), null, null),
                LanguageOption(stringResource(R.string.language_english), R.drawable.flag_en, "en"),
                LanguageOption(stringResource(R.string.language_portuguese), R.drawable.flag_pt_pt, "pt-PT")
            ),
            modifier = Modifier.align(Alignment.TopEnd).statusBarsPadding().padding(16.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth().widthIn(max = 1000.dp).padding(24.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Instructions()
                HorizontalDivider()
                val errorStr = stringResource(R.string.invalid_server_address)
                AddressForm(
                    input = input,
                    error = error,
                    onInputChange = { input = it; error = null },
                    onContinue = {
                        val address = input.trim()
                        val uri = address.toUri()
                        val scheme = uri.scheme?.lowercase()
                        if (scheme !in listOf("http", "https") || uri.host.isNullOrBlank()
                        ) { error = errorStr }
                        else if (scheme == "http") { pendingHttpAddress = address }
                        else { saveAddress(address) }
                    }
                )
            }
        }
    }
    pendingHttpAddress?.let { address ->
        AlertDialog(
            onDismissRequest = { pendingHttpAddress = null },
            title = { Text(stringResource(R.string.http_dialog_title)) },
            text = { Text(stringResource(R.string.http_dialog_message)) },
            confirmButton = { Button(onClick = { saveAddress(address) }) { Text(stringResource(R.string.continue_with_http)) } },
            dismissButton = { TextButton(onClick = { pendingHttpAddress = null }) { Text(stringResource(
                R.string.go_back)) } }
        )
    }
}
