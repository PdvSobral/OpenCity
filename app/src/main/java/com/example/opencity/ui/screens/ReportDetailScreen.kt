package com.example.opencity.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.opencity.R
import com.example.opencity.models.ReportStatus
import com.example.opencity.models.toColourValue
import com.example.opencity.models.toStringRes
import com.example.opencity.ui.components.TopBar


@Composable
fun ReportDetailScreen(
    reportId: Int,
    onReturn: () -> Unit,
) {
    var vote by rememberSaveable(67) { mutableIntStateOf(0) }
    val signedIn = false // will pull from ModelView
    var following by rememberSaveable(false) { mutableStateOf(false) }

    var draft by rememberSaveable("Draft of a comment...") { mutableStateOf("") }


    // makes no sense why diferent. create a model for a comment.
    var authors by rememberSaveable { mutableStateOf(arrayListOf<String>()) }
    var comments by rememberSaveable { mutableStateOf(arrayListOf<String>()) }


    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(tittle = "${stringResource(R.string.report_detail_title)} DEBUG: $reportId", stringResource(R.string.report_detail_description), true, onReturn)
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text("Report Tittle", style = MaterialTheme.typography.headlineSmall)
                    Text("Report Category", style = MaterialTheme.typography.labelLarge)
                    Text(stringResource(ReportStatus.REPORTED.toStringRes()), color = ReportStatus.REPORTED.toColourValue())
                }
                item {
                    DetailField(stringResource(R.string.report_detail_description), "Report Description")
                }
                item { DetailField(stringResource(R.string.report_detail_location), "GPS coords") }
                item { DetailField(stringResource(R.string.report_detail_date), "Reported on X/Y/Z") }
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
                        onClick = {}
                    ) { Text(stringResource(R.string.report_detail_send)) }
                }
            }
        }
    }
}

@Composable
private fun DetailField(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}
