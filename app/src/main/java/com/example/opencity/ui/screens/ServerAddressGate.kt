package com.example.opencity.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.opencity.storage.SettingsRepository
import com.example.opencity.storage.AppSettings
import kotlinx.coroutines.launch
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.opencity.R
import com.example.opencity.models.LanguageOption
import com.example.opencity.storage.SettingsKeys
import com.example.opencity.ui.components.LanguageSelectorButton


@Composable
fun ServerAddressGate(content: @Composable (String) -> Unit) {
    val context = LocalContext.current
    val repository = remember(context.applicationContext) {
        SettingsRepository(context.applicationContext)
    }
    val scope = rememberCoroutineScope()
    val settings by produceState<AppSettings?>(
        initialValue = null,
        key1 = repository
    ) { repository.settings.collect { value = it } }

    val currentSettings = settings
    if (currentSettings == null) {
        // Loading... No one can escape the damn loading screen... Muahahahaha! (*mental sanity left chat*)
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }

    val savedAddress = currentSettings.serverAddress.takeIf { it.isNotBlank() } // default is "", quite literally

    var input by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var pendingHttpAddress by remember { mutableStateOf<String?>(null) }

    fun saveAddress(address: String) {
        scope.launch { repository.setSettingValue(SettingsKeys.SERVER_ADDRESS, address); }
        pendingHttpAddress = null
    }

    if (savedAddress != null) {
        content(savedAddress) // pass the string to rest of code
        return // return to caller
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
            languages = listOf(
                LanguageOption("System Default", null, null),
                LanguageOption("English", R.drawable.flag_en, "en"),
                LanguageOption("Português", R.drawable.flag_pt_pt, "pt-PT")
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

@Composable
private fun Instructions(modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = stringResource(R.string.connect_title),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.connect_instructions),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = stringResource(R.string.security_note),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun AddressForm(
    input: String,
    error: String?,
    onInputChange: (String) -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = stringResource(R.string.server_address),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            value = input,
            onValueChange = onInputChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text(stringResource(R.string.server_address_placeholder)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
            isError = error != null,
            supportingText = error?.let { message -> { Text(message) } }
        )
        Button(onClick = onContinue, modifier = Modifier.fillMaxWidth()) { Text("Continue") }
    }
}
