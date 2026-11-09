package com.example.opencity.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.opencity.R
import com.example.opencity.models.Account
import com.example.opencity.models.AccountRole
import com.example.opencity.models.Report
import com.example.opencity.models.ReportStatus
import com.example.opencity.models.toStringRes
import com.example.opencity.ui.theme.OpenCityTheme

/** Interface-only state, retained when returning to this report during this session.
 * The host supplies the authenticated account; null means anonymous, read-only access.
 * No server writes are performed by this prototype.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportDetailScreen(
    report: Report,
    onBack: () -> Unit,
    account: Account? = null,
    onStatusChanged: (ReportStatus) -> Unit = {}
) {
    var vote by rememberSaveable(report.id, account?.id) { mutableStateOf(0) }
    var following by rememberSaveable(report.id, account?.id) { mutableStateOf(false) }
    var draft by rememberSaveable(report.id, account?.id) { mutableStateOf("") }
    var comments by rememberSaveable(report.id) { mutableStateOf(arrayListOf<String>()) }
    var authors by rememberSaveable(report.id) { mutableStateOf(arrayListOf<String>()) }
    var statusDialog by rememberSaveable(report.id) { mutableStateOf(false) }
    var selectedStatus by rememberSaveable(report.id) { mutableStateOf(report.status.name) }
    val signedIn = account != null
    val administrator = account?.role == AccountRole.ADMINISTRATOR

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0, 0, 0, 0),
                title = { Text(stringResource(R.string.report_detail_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.report_detail_back))
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).imePadding(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(report.title, style = MaterialTheme.typography.headlineSmall)
                Text(report.category, style = MaterialTheme.typography.labelLarge)
                Text(stringResource(report.status.toStringRes()), color = MaterialTheme.colorScheme.primary)
            }
            item {
                DetailField(stringResource(R.string.report_detail_description), report.description)
            }
            item { DetailField(stringResource(R.string.report_detail_location), report.address) }
            item { DetailField(stringResource(R.string.report_detail_date), report.date) }
            item {
                HorizontalDivider()
                Text(stringResource(R.string.report_detail_actions), style = MaterialTheme.typography.titleMedium)
                if (!signedIn) {
                    Text(stringResource(R.string.report_detail_sign_in), style = MaterialTheme.typography.bodySmall)
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    FilterChip(
                        selected = vote == 1,
                        onClick = { vote = if (vote == 1) 0 else 1 },
                        enabled = signedIn,
                        label = { Text(stringResource(R.string.report_detail_confirm)) }
                    )
                    FilterChip(
                        selected = vote == -1,
                        onClick = { vote = if (vote == -1) 0 else -1 },
                        enabled = signedIn,
                        label = { Text(stringResource(R.string.report_detail_dispute)) }
                    )
                    FilterChip(
                        selected = following,
                        onClick = { following = !following },
                        enabled = signedIn,
                        label = { Text(stringResource(if (following) R.string.report_detail_following else R.string.report_detail_follow)) }
                    )
                    if (administrator) {
                        OutlinedButton(onClick = {
                            selectedStatus = report.status.name
                            statusDialog = true
                        }) { Text(stringResource(R.string.report_detail_change_status)) }
                    }
                }
            }
            item {
                HorizontalDivider()
                Text(stringResource(R.string.report_detail_comments, comments.size), style = MaterialTheme.typography.titleMedium)
                if (comments.isEmpty()) Text(stringResource(R.string.report_detail_no_comments))
            }
            itemsIndexed(comments) { index, comment ->
                OutlinedCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(authors[index], style = MaterialTheme.typography.labelLarge)
                        Text(comment)
                    }
                }
            }
            item {
                OutlinedTextField(
                    value = draft,
                    onValueChange = { if (it.length <= 2000) draft = it },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = signedIn,
                    minLines = 3,
                    maxLines = 6,
                    label = { Text(stringResource(R.string.report_detail_add_comment)) },
                    supportingText = { Text("${draft.length}/2000") }
                )
                Button(
                    enabled = signedIn && draft.isNotBlank(),
                    onClick = {
                        val author = account ?: return@Button
                        val text = draft.trim()
                        if (text.isNotEmpty()) {
                            authors = ArrayList(authors).apply { add(author.displayName ?: author.username) }
                            comments = ArrayList(comments).apply { add(text) }
                            draft = ""
                        }
                    }
                ) { Text(stringResource(R.string.report_detail_send)) }
            }
        }
    }

    if (statusDialog && administrator) {
        AlertDialog(
            onDismissRequest = { statusDialog = false },
            title = { Text(stringResource(R.string.report_detail_change_status)) },
            text = {
                LazyColumn {
                    itemsIndexed(ReportStatus.entries.toList()) { _, status ->
                        FilterChip(
                            selected = selectedStatus == status.name,
                            onClick = { selectedStatus = status.name },
                            label = { Text(stringResource(status.toStringRes())) }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onStatusChanged(ReportStatus.valueOf(selectedStatus))
                    statusDialog = false
                }) { Text(stringResource(R.string.report_detail_save)) }
            },
            dismissButton = {
                TextButton(onClick = { statusDialog = false }) {
                    Text(stringResource(R.string.report_detail_cancel))
                }
            }
        )
    }
}

@Composable
private fun DetailField(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}


@Preview(showBackground = true)
@Composable
private fun ReportDetailUserPreview() {
    OpenCityTheme {
        ReportDetailScreen(
            report = previewReport,
            account = Account(1, "alex", "Alex"),
            onBack = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ReportDetailAdminPreview() {
    var report by remember { mutableStateOf(previewReport) }
    OpenCityTheme {
        ReportDetailScreen(
            report = report,
            account = Account(2, "admin", role = AccountRole.ADMINISTRATOR),
            onBack = {},
            onStatusChanged = { report = report.copy(status = it) }
        )
    }
}

private val previewReport = Report(
    1, "Broken streetlight", "Public lighting",
    "The streetlight has not been working for several days.",
    "Main Avenue, 120", ReportStatus.REPORTED, "Today"
)
