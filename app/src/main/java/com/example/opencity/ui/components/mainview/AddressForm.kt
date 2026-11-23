package com.example.opencity.ui.components.mainview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.opencity.R


@Composable
fun AddressForm(
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
        Button(onClick = onContinue, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.continue_button)) }
    }
}
