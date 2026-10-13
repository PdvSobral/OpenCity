package com.example.opencity.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.opencity.R
import com.example.opencity.models.Account
import com.example.opencity.models.AccountRole
import com.example.opencity.ui.components.TopBar

@Composable
fun AccountScreen(
    account: Account? = null, // TODO: Later when making a more robust system, maybe force to be passed smething, even if null
    onEditProfile: () -> Unit = {},
    onContributionHistory: () -> Unit = {},
    onExportData: () -> Unit = {},
    onNotificationSettings: () -> Unit = {},
    onSecuritySettings: () -> Unit = {},
    onAppSettings: () -> Unit = {},
    onAbout: () -> Unit = {},
    onLogout: () -> Unit = {},
    onDeleteAccount: () -> Unit = {},
    onAdminDashboard: () -> Unit = {},
    onCreateAccount: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TopBar(stringResource(R.string.top_bar_account))
        Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f).fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AccountHeader(
                    account = account,
                    onEditProfile = onEditProfile
                )
                AccountSection(title = stringResource(R.string.account_section_your_activity_title)) {
                    AccountAction(
                        icon = Icons.Outlined.Star,
                        title = stringResource(R.string.account_section_your_activity_contribution),
                        subtitle = stringResource(R.string.your_activity_contribution_history_subtitle),
                        onClick = onContributionHistory
                    )
                    AccountAction(
                        icon = Icons.Outlined.Download,
                        title = stringResource(R.string.your_activity_section_export_ur_data_title),
                        subtitle = stringResource(R.string.ur_activity_section_export_ur_data_subtitle),
                        onClick = onExportData
                    )
                }
                AccountSection(title = stringResource(R.string.account_section_account_settings)) {
                    AccountAction(
                        icon = Icons.Outlined.Notifications,
                        title = stringResource(R.string.account_settings_notifications_title),
                        subtitle = stringResource(R.string.account_settings_notifications_subtitle), // nao sei como fica melhor... "Faça a gestão das notificações de relatórios e de atividade" ou ... "Gerir notificações de relatórios e de atividade"
                        onClick = onNotificationSettings
                    )
                    AccountAction(
                        icon = Icons.Outlined.Lock,
                        title = stringResource(R.string.account_settings_security_title),
                        subtitle = stringResource(R.string.account_settings_security_subtitle),  // nao sei como fica melhor... "Faça a gestão da sua palavra-passe e métodos de início de sessão" ou ... "Gerir a sua palavra-passe e métodos de início de sessão"
                        onClick = onSecuritySettings
                    )
                    AccountAction(
                        icon = Icons.Outlined.Settings,
                        title = stringResource(R.string.account_settings_application_settings_title),
                        subtitle = stringResource(R.string.account_settings_application_settings_subtitle),
                        onClick = onAppSettings
                    )
                }
                if ((account?.role ?: AccountRole.USER) == AccountRole.ADMINISTRATOR) {
                    AccountSection(title = "Administration") {
                        AccountAction(
                            icon = Icons.Outlined.AdminPanelSettings,
                            title = "Administration dashboard",
                            subtitle = "Manage reports, users, and categories",
                            onClick = onAdminDashboard
                        )
                    }
                }
                AccountSection(title = stringResource(R.string.account_section_other_section)) {
                    AccountAction(
                        icon = Icons.Outlined.Info,
                        title = stringResource(R.string.account_section_other_title),
                        subtitle = stringResource(R.string.account_section_other_subtitle),
                        onClick = onAbout
                    )
                }
                if (account != null){
                    OutlinedButton(
                        onClick = onLogout,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Logout,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Log out")
                    }
                    Button(
                        onClick = onDeleteAccount,
                        modifier = Modifier.fillMaxWidth(),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Delete account")
                    }
                } else {
                    Button(
                        onClick = onCreateAccount,
                        modifier = Modifier.fillMaxWidth(),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PersonAdd,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Create an Account!")
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun AccountHeader(
    account: Account?,
    onEditProfile: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 14.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(0.dp)
            ) {
                AccountAvatar(account)
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(0.dp)
                    ){
                        Text(
                            text = (account?.displayName ?: "Anonymous").takeIf { it.isNotBlank() } ?: (account?.username ?: "anonymous"),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (account != null) {
                            IconButton(onClick = onEditProfile) {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = "Edit profile"
                                )
                            }
                        }
                    }
                    // TODO: Move it more up, reducing margins
                    Text(
                        text = "@${account?.username ?: "anonymous"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Member since ${(account?.createdAt ?: "N/A")}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
                    )
                }
                Spacer(modifier = Modifier.weight(1f).fillMaxWidth())
                StatCard(numeroPontos = (account?.points ?: 0).toString())
            }
        }
    }
}

@Composable
private fun AccountAvatar(account: Account?) {
    val initials = buildString {
        (account?.displayName ?: "Anonymous").takeIf { it.isNotBlank() } ?: (account?.username ?: "anonymous").split(" ").take(2).forEach { name -> name.firstOrNull()?.let { append(it.uppercase()) } }
        if (isEmpty()) { append((account?.username ?: "anonymous").take(2).uppercase()) }
    }
    Box(
        modifier = Modifier.size(76.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun StatCard(numeroPontos: String) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(top = 6.dp, bottom = 4.dp, start = 8.dp, end = 10.dp)) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = numeroPontos,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun AccountSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) { Column { content() } }
    }
}

@Composable
private fun AccountAction(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        headlineContent = {Text(text = title, fontWeight = FontWeight.Medium)},
        supportingContent = {Text(text = subtitle, maxLines = 2, overflow = TextOverflow.Ellipsis)},
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )
}
