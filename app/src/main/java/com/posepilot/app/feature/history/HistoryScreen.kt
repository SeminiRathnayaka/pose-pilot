package com.posepilot.app.feature.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppCardStyles
import com.posepilot.app.core.theme.AppColors
import com.posepilot.app.core.theme.AppSpacing
import com.posepilot.app.core.theme.PosePilotTheme
import com.posepilot.app.shared.data.FavoritesStore
import com.posepilot.app.shared.data.SamplePoses
import com.posepilot.app.shared.widgets.CategoryChip
import com.posepilot.app.shared.widgets.EmptyStateView
import com.posepilot.app.shared.widgets.PoseCard
import com.posepilot.app.shared.widgets.SecondaryButton
import com.posepilot.app.shared.widgets.ScreenHeader

private enum class HistoryFilter(val key: String) {
    ALL("all"),
    FAVORITES("favorites"),
}

@Composable
fun HistoryScreen(
    onOpenExplore: () -> Unit,
    modifier: Modifier = Modifier,
    onOpenPose: (String) -> Unit = {},
) {
    val favorites by FavoritesStore.favoriteIds.collectAsStateWithLifecycle()

    var filter by rememberSaveable { mutableStateOf(HistoryFilter.ALL.key) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_history")
            .padding(
                start = AppSpacing.screenHorizontal,
                end = AppSpacing.screenHorizontal,
                top = AppSpacing.screenVertical,
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ScreenHeader(title = stringResource(R.string.history_title))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CategoryChip(
                label = stringResource(R.string.filter_all),
                selected = filter == HistoryFilter.ALL.key,
                onClick = { filter = HistoryFilter.ALL.key },
                testTag = "filter_all",
            )
            CategoryChip(
                label = stringResource(R.string.filter_favorites),
                selected = filter == HistoryFilter.FAVORITES.key,
                onClick = { filter = HistoryFilter.FAVORITES.key },
                testTag = "filter_favorites",
            )
        }

        when (filter) {
            HistoryFilter.ALL.key -> {
                PhotoEmptyState(onOpenExplore = onOpenExplore)
            }

            HistoryFilter.FAVORITES.key -> {
                FavoritesGrid(
                    favoriteIds = favorites,
                    onOpenPose = onOpenPose,
                )
            }
        }
    }
}

@Composable
private fun PhotoEmptyState(onOpenExplore: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("history_empty"),
        shape = AppCardStyles.shape,
        color = AppColors.Black,
        contentColor = AppColors.White,
        border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.history_empty_title),
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.White,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.history_empty_body),
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            SecondaryButton(
                text = stringResource(R.string.button_explore_poses),
                onClick = onOpenExplore,
                modifier = Modifier.testTag("button_explore_poses"),
            )
        }
    }
}

@Composable
private fun FavoritesGrid(
    favoriteIds: Set<String>,
    onOpenPose: (String) -> Unit,
) {
    val favoritePoses = SamplePoses.all.filter { it.id in favoriteIds }
    if (favoritePoses.isEmpty()) {
        EmptyStateView(
            title = stringResource(R.string.home_favorites_empty),
            body = stringResource(R.string.history_empty_body),
            icon = Icons.Filled.FavoriteBorder,
            testTag = "history_favorites_empty",
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = AppSpacing.sectionVertical),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(favoritePoses, key = { it.id }) { pose ->
                PoseCard(
                    pose = pose,
                    onClick = { onOpenPose(pose.id) },
                    isFavorite = true,
                    onToggleFavorite = { FavoritesStore.toggle(pose.id) },
                )
            }
        }
    }
}

@Preview(name = "History", showBackground = true)
@Composable
private fun HistoryPreview() {
    PosePilotTheme {
        HistoryScreen(onOpenExplore = {})
    }
}
