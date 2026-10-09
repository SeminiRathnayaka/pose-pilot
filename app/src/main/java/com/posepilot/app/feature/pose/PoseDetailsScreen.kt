package com.posepilot.app.feature.pose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppCardStyles
import com.posepilot.app.core.theme.AppColors
import com.posepilot.app.core.theme.AppRadius
import com.posepilot.app.core.theme.AppSpacing
import com.posepilot.app.core.theme.PosePilotTheme
import com.posepilot.app.shared.data.FavoritesStore
import com.posepilot.app.shared.data.SamplePoses
import com.posepilot.app.shared.model.Pose
import com.posepilot.app.shared.model.PoseCategory
import com.posepilot.app.shared.model.PoseDifficulty
import com.posepilot.app.shared.model.PoseMode
import com.posepilot.app.shared.widgets.FavoriteIconButton
import com.posepilot.app.shared.widgets.PoseThumbnail
import com.posepilot.app.shared.widgets.PrimaryButton
import com.posepilot.app.shared.widgets.ScreenHeader

@Composable
fun PoseDetailsScreen(
    poseId: String,
    onBack: () -> Unit,
    onTryPose: (Pose, PoseMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pose = SamplePoses.byId(poseId)
    val favorites by FavoritesStore.favoriteIds.collectAsStateWithLifecycle()

    if (pose == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .testTag("screen_pose_details"),
        ) {
            PoseDetailsTopBar(onBack = onBack, pose = null, isFavorite = false, onToggleFavorite = {})
            Text(
                text = stringResource(R.string.explore_empty_title),
                style = MaterialTheme.typography.bodyLarge,
                color = AppColors.LightGray,
                modifier = Modifier.padding(AppSpacing.screenHorizontal),
            )
        }
        return
    }

    PoseDetailsContent(
        pose = pose,
        isFavorite = pose.id in favorites,
        onBack = onBack,
        onToggleFavorite = { FavoritesStore.toggle(pose.id) },
        onTryPose = { onTryPose(pose, pose.mode) },
        modifier = modifier,
    )
}

@Composable
private fun PoseDetailsContent(
    pose: Pose,
    isFavorite: Boolean,
    onBack: () -> Unit,
    onToggleFavorite: () -> Unit,
    onTryPose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_pose_details")
            .verticalScroll(rememberScrollState()),
    ) {
        PoseDetailsTopBar(
            onBack = onBack,
            pose = pose,
            isFavorite = isFavorite,
            onToggleFavorite = onToggleFavorite,
        )
        Column(
            modifier = Modifier.padding(
                start = AppSpacing.screenHorizontal,
                end = AppSpacing.screenHorizontal,
                bottom = AppSpacing.sectionVertical,
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PoseThumbnail(
                pose = pose,
                large = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f),
            )
            Text(
                text = stringResource(R.string.details_thumbnail_caption),
                style = MaterialTheme.typography.labelSmall,
                color = AppColors.Gray,
            )

            ScreenHeader(title = pose.name)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoPill(text = stringResource(pose.category.labelRes))
                InfoPill(text = stringResource(pose.difficulty.labelRes))
                InfoPill(text = stringResource(pose.mode.labelRes))
            }

            Text(
                text = pose.description,
                style = MaterialTheme.typography.bodyLarge,
                color = AppColors.LightGray,
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = AppCardStyles.shape,
                color = AppColors.Navy,
                contentColor = AppColors.White,
                border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = stringResource(R.string.details_instructions),
                        style = MaterialTheme.typography.titleMedium,
                        color = AppColors.White,
                    )
                    pose.instructions.forEachIndexed { index, instruction ->
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text(
                                text = "${index + 1}",
                                style = MaterialTheme.typography.labelLarge,
                                color = AppColors.Gray,
                                modifier = Modifier.testTag("instruction_index_$index"),
                            )
                            Text(
                                text = instruction,
                                style = MaterialTheme.typography.bodyMedium,
                                color = AppColors.LightGray,
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            PrimaryButton(
                text = stringResource(R.string.button_try_pose),
                onClick = onTryPose,
                modifier = Modifier.testTag("button_try_pose"),
            )
        }
    }
}

@Composable
private fun PoseDetailsTopBar(
    onBack: () -> Unit,
    pose: Pose?,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBack, modifier = Modifier.testTag("top_bar_back")) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.action_back),
                tint = AppColors.White,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (pose != null) {
            FavoriteIconButton(
                isFavorite = isFavorite,
                onToggle = onToggleFavorite,
                poseId = pose.id,
            )
        }
    }
}

@Composable
private fun InfoPill(text: String) {
    Surface(
        shape = RoundedCornerShape(AppRadius.pill),
        color = AppColors.DarkGray,
        contentColor = AppColors.LightGray,
        border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
        )
    }
}

@Preview(name = "Pose details", showBackground = true)
@Composable
private fun PoseDetailsPreview() {
    PosePilotTheme {
        val pose = Pose(
            id = "relaxed_standing",
            name = "Relaxed Standing",
            mode = PoseMode.SOLO,
            category = PoseCategory.STANDING,
            difficulty = PoseDifficulty.EASY,
            description = "A natural standing pose.",
            instructions = listOf("Stand comfortably.", "Relax your shoulders."),
        )
        PoseDetailsContent(
            pose = pose,
            isFavorite = false,
            onBack = {},
            onToggleFavorite = {},
            onTryPose = {},
        )
    }
}
