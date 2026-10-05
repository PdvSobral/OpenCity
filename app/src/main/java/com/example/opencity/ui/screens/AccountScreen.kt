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
import androidx.compose.material.icons.outlined.AccountCircle
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.opencity.models.Account
import com.example.opencity.models.AccountRole
import com.example.opencity.ui.components.TopBar

@Composable
fun AccountScreen(
    account: Account,
    onEditProfile: () -> Unit = {},
    onContributionHistory: () -> Unit = {},
    onExportData: () -> Unit = {},
    onNotificationSettings: () -> Unit = {},
    onSecuritySettings: () -> Unit = {},
    onAppSettings: () -> Unit = {},
    onAbout: () -> Unit = {},
    onLogout: () -> Unit = {},
    onDeleteAccount: () -> Unit = {},
    onAdminDashboard: () -> Unit = {}
) {
    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        TopBar("Account")
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
            AccountStats(
                points = account.points,
                role = account.role
            )
            AccountSection(title = "Your activity") {
                AccountAction(
                    icon = Icons.Outlined.Star,
                    title = "Contribution history",
                    subtitle = "View your reports, comments, and confirmations",
                    onClick = onContributionHistory
                )
                AccountAction(
                    icon = Icons.Outlined.Download,
                    title = "Export your data",
                    subtitle = "Download your reports and contributions",
                    onClick = onExportData
                )
            }
            AccountSection(title = "Account settings") {
                AccountAction(
                    icon = Icons.Outlined.Notifications,
                    title = "Notifications",
                    subtitle = "Manage report and activity notifications",
                    onClick = onNotificationSettings
                )
                AccountAction(
                    icon = Icons.Outlined.Lock,
                    title = "Security",
                    subtitle = "Manage your password and login methods",
                    onClick = onSecuritySettings
                )
                AccountAction(
                    icon = Icons.Outlined.Settings,
                    title = "Application settings",
                    subtitle = "Theme, map, offline data, and preferences",
                    onClick = onAppSettings
                )
            }
            if (account.role == AccountRole.ADMINISTRATOR) {
                AccountSection(title = "Administration") {
                    AccountAction(
                        icon = Icons.Outlined.AdminPanelSettings,
                        title = "Administration dashboard",
                        subtitle = "Manage reports, users, and categories",
                        onClick = onAdminDashboard
                    )
                }
            }
            AccountSection(title = "Other") {
                AccountAction(
                    icon = Icons.Outlined.Info,
                    title = "About OpenCity",
                    subtitle = "Terms of use, licenses, and project information",
                    onClick = onAbout
                )
            }
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
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun AccountHeader(
    account: Account,
    onEditProfile: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AccountAvatar(account)
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = account.displayName ?.takeIf { it.isNotBlank() } ?: account.username,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "@${account.username}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    RoleBadge(account.role)
                }
                IconButton(onClick = onEditProfile) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit profile"
                    )
                }
            }
            account.createdAt?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Member since $it",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
                )
            }
        }
    }
}

@Composable
private fun AccountAvatar(account: Account) {
    val initials = buildString {
        account.displayName?.split(" ")?.take(2)
            ?.forEach { name -> name.firstOrNull()?.let { append(it.uppercase()) } }
        if (isEmpty()) { append(account.username.take(2).uppercase()) }
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
private fun RoleBadge(role: AccountRole) {
    val roleName = when (role) {
        AccountRole.USER -> "Citizen"
        AccountRole.ADMINISTRATOR -> "Administrator"
    }
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
    ) {
        Text(
            text = roleName,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun AccountStats(
    points: Int,
    role: AccountRole
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            value = points.toString(),
            label = "Points",
            icon = Icons.Outlined.Star
        )
        StatCard(
            modifier = Modifier.weight(1f),
            value = when (role) {
                AccountRole.USER -> "User"
                AccountRole.ADMINISTRATOR -> "Admin"
            },
            label = "Access level",
            icon = Icons.Outlined.AccountCircle
        )
    }
}

@Composable
private fun StatCard(
    modifier: Modifier,
    value: String,
    label: String,
    icon: ImageVector
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
