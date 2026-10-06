package com.posepilot.app.feature.poses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppCardStyles
import com.posepilot.app.core.theme.PosePilotTheme
import com.posepilot.app.feature.home.PlaceholderCatalog
import com.posepilot.app.shared.model.Pose
import com.posepilot.app.shared.widgets.ScreenHeader

@Composable
fun PoseLibraryScreen(modifier: Modifier = Modifier) {
    val poses = PlaceholderCatalog.poses

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_poses")
            .padding(horizontal = 20.dp, vertical = 24.dp),
    ) {
        ScreenHeader(
            title = stringResource(R.string.nav_poses),
            subtitle = stringResource(R.string.placeholder_poses_body),
        )
        Text(
            text = stringResource(R.string.sample_catalog),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(poses, key = { it.id }) { pose ->
                PoseRow(pose = pose)
            }
        }
    }
}

@Composable
private fun PoseRow(pose: Pose) {
    Surface(
        shape = AppCardStyles.shape,
        color = AppCardStyles.default().containerColor,
        tonalElevation = 1.dp,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = pose.name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = pose.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp),
            )
            Text(
                text = stringResource(pose.mode.labelRes),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Preview(name = "Poses light", showBackground = true)
@Composable
private fun PoseLibraryScreenPreview() {
    PosePilotTheme {
        PoseLibraryScreen()
    }
}
