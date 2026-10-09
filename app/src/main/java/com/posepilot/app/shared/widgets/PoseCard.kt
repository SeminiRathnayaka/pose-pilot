package com.posepilot.app.shared.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppColors
import com.posepilot.app.core.theme.AppCardStyles
import com.posepilot.app.shared.model.Pose
import com.posepilot.app.shared.model.PoseMode

@Composable
fun poseModeIcon(mode: PoseMode): Painter = when (mode) {
    PoseMode.SOLO -> painterResource(R.drawable.ic_poses)
    PoseMode.COUPLE -> painterResource(R.drawable.ic_couple)
    PoseMode.GROUP -> painterResource(R.drawable.ic_group)
    PoseMode.FREE -> painterResource(R.drawable.ic_free)
}

@Composable
fun PoseThumbnail(
    pose: Pose,
    modifier: Modifier = Modifier,
    large: Boolean = false,
) {
    Box(
        modifier = modifier
            .background(color = AppColors.DarkGray, shape = AppCardStyles.shape)
            .padding(1.dp)
            .background(color = AppColors.Black, shape = AppCardStyles.shape),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = poseModeIcon(pose.mode),
                contentDescription = null,
                modifier = Modifier.size(if (large) 56.dp else 36.dp),
                tint = AppColors.Gray,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.sample_data_caption),
                style = MaterialTheme.typography.labelSmall,
                color = AppColors.Gray,
            )
        }
    }
}

@Composable
fun FavoriteIconButton(
    isFavorite: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    testTagPrefix: String = "favorite_toggle",
    poseId: String = "",
) {
    IconButton(
        onClick = onToggle,
        modifier = modifier.testTag(if (poseId.isEmpty()) testTagPrefix else "${testTagPrefix}_$poseId"),
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = stringResource(
                if (isFavorite) R.string.favorite_remove else R.string.favorite_add,
            ),
            tint = if (isFavorite) AppColors.White else AppColors.Gray,
        )
    }
}

@Composable
fun PoseCard(
    pose: Pose,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    onToggleFavorite: (() -> Unit)? = null,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.testTag("pose_card_${pose.id}"),
        shape = AppCardStyles.shape,
        color = AppColors.Navy,
        contentColor = AppColors.White,
        border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
    ) {
        Column {
            Box {
                PoseThumbnail(
                    pose = pose,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 3f),
                )
                if (onToggleFavorite != null) {
                    FavoriteIconButton(
                        isFavorite = isFavorite,
                        onToggle = onToggleFavorite,
                        poseId = pose.id,
                        modifier = Modifier.align(Alignment.TopEnd),
                    )
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = pose.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = AppColors.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(pose.category.labelRes),
                    style = MaterialTheme.typography.labelSmall,
                    color = AppColors.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
fun PoseCompactCard(
    pose: Pose,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    onToggleFavorite: (() -> Unit)? = null,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.testTag("pose_card_${pose.id}"),
        shape = AppCardStyles.shape,
        color = AppColors.Navy,
        contentColor = AppColors.White,
        border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            PoseThumbnail(
                pose = pose,
                modifier = Modifier
                    .width(84.dp)
                    .height(84.dp),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
            ) {
                Text(
                    text = pose.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = AppColors.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${stringResource(pose.category.labelRes)} · ${stringResource(pose.difficulty.labelRes)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppColors.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (onToggleFavorite != null) {
                FavoriteIconButton(
                    isFavorite = isFavorite,
                    onToggle = onToggleFavorite,
                    poseId = pose.id,
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}
