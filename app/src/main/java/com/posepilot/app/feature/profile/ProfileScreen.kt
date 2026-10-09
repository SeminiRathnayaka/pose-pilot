package com.posepilot.app.feature.profile

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppCardStyles
import com.posepilot.app.core.theme.AppColors
import com.posepilot.app.core.theme.AppSpacing
import com.posepilot.app.core.theme.PosePilotTheme
import com.posepilot.app.shared.data.FavoritesStore
import com.posepilot.app.shared.widgets.ScreenHeader
import com.posepilot.app.shared.widgets.SettingsGroup
import com.posepilot.app.shared.widgets.SettingsRow
import com.posepilot.app.shared.widgets.StatisticCard

@Composable
fun ProfileScreen(
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val favorites by FavoritesStore.favoriteIds.collectAsStateWithLifecycle()

    ProfileContent(
        favoriteCount = favorites.size,
        onOpenSettings = onOpenSettings,
        modifier = modifier,
    )
}

@Composable
private fun ProfileContent(
    favoriteCount: Int,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val editProfileMessage = stringResource(R.string.toast_edit_profile)

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_profile")
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = AppSpacing.screenHorizontal,
                vertical = AppSpacing.screenVertical,
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        ScreenHeader(title = stringResource(R.string.profile_title))

        ProfileIdentityCard(
            onEditProfile = {
                Toast.makeText(
                    context,
                    editProfileMessage,
                    Toast.LENGTH_SHORT,
                ).show()
            },
        )

        SettingsGroup {
            SettingsRow(
                title = stringResource(R.string.entry_settings),
                onClick = onOpenSettings,
                testTag = "profile_row_settings",
            )
            SettingsRow(
                title = stringResource(R.string.entry_privacy),
                onClick = onOpenSettings,
                testTag = "profile_row_privacy",
            )
            SettingsRow(
                title = stringResource(R.string.entry_help_about),
                onClick = onOpenSettings,
                testTag = "profile_row_help",
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatisticCard(
                    value = "0",
                    label = stringResource(R.string.stat_photos),
                    icon = painterResource(R.drawable.ic_camera),
                    modifier = Modifier.weight(1f),
                    testTag = "stat_photos",
                )
                StatisticCard(
                    value = "0",
                    label = stringResource(R.string.stat_poses_explored),
                    icon = painterResource(R.drawable.ic_poses),
                    modifier = Modifier.weight(1f),
                    testTag = "stat_poses_explored",
                )
                StatisticCard(
                    value = favoriteCount.toString(),
                    label = stringResource(R.string.stat_favorites),
                    icon = painterResource(R.drawable.ic_history),
                    modifier = Modifier.weight(1f),
                    testTag = "stat_favorites",
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("profile_journey"),
            shape = AppCardStyles.shape,
            color = AppColors.Black,
            contentColor = AppColors.White,
            border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = AppColors.Gray,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.journey_title),
                        style = MaterialTheme.typography.titleSmall,
                        color = AppColors.White,
                    )
                }
                Text(
                    text = stringResource(R.string.journey_body),
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.LightGray,
                )
            }
        }
    }
}

@Composable
private fun ProfileIdentityCard(onEditProfile: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("profile_identity"),
        shape = AppCardStyles.shape,
        color = AppColors.Navy,
        contentColor = AppColors.White,
        border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(color = AppColors.DarkGray, shape = CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = AppColors.Gray,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.profile_name_placeholder),
                    style = MaterialTheme.typography.titleMedium,
                    color = AppColors.White,
                )
                Text(
                    text = stringResource(R.string.button_edit_profile),
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.Gray,
                )
            }
            IconButton(onClick = onEditProfile, modifier = Modifier.testTag("button_edit_profile")) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(R.string.button_edit_profile),
                    tint = AppColors.LightGray,
                )
            }
        }
    }
}

@Preview(name = "Profile", showBackground = true)
@Composable
private fun ProfilePreview() {
    PosePilotTheme {
        ProfileContent(
            favoriteCount = 0,
            onOpenSettings = {},
        )
    }
}
