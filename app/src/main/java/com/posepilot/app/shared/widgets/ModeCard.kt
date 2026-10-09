package com.posepilot.app.shared.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.posepilot.app.R
import com.posepilot.app.core.constants.AppConstants
import com.posepilot.app.core.theme.AppColors
import com.posepilot.app.core.theme.AppCardStyles
import com.posepilot.app.shared.model.PoseMode

@Composable
fun ModeCard(
    mode: PoseMode,
    icon: Painter?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    emphasized: Boolean = false,
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = AppConstants.MODE_CARD_MIN_HEIGHT_DP.dp)
            .testTag("mode_card_${mode.key}"),
        shape = AppCardStyles.shape,
        color = if (emphasized) AppColors.Navy else AppColors.DarkGray,
        contentColor = AppColors.White,
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = AppColors.FaintBorder,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = AppColors.Black,
                    modifier = Modifier.size(44.dp),
                ) {
                    if (icon != null) {
                        Icon(
                            painter = icon,
                            contentDescription = null,
                            modifier = Modifier.padding(10.dp),
                            tint = AppColors.LightGray,
                        )
                    }
                }
                Text(
                    text = stringResource(mode.labelRes),
                    style = MaterialTheme.typography.titleMedium,
                    color = AppColors.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(modeDescriptionRes(mode)),
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.LightGray,
            )
        }
    }
}

internal fun modeDescriptionRes(mode: PoseMode): Int = when (mode) {
    PoseMode.SOLO -> R.string.pose_mode_solo_desc
    PoseMode.COUPLE -> R.string.pose_mode_couple_desc
    PoseMode.GROUP -> R.string.pose_mode_group_desc
    PoseMode.FREE -> R.string.pose_mode_free_desc
}
