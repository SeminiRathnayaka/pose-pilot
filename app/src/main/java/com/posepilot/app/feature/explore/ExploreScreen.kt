package com.posepilot.app.feature.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppSpacing
import com.posepilot.app.core.theme.PosePilotTheme
import com.posepilot.app.shared.data.FavoritesStore
import com.posepilot.app.shared.model.PoseCategory
import com.posepilot.app.shared.widgets.CategoryChip
import com.posepilot.app.shared.widgets.EmptyStateView
import com.posepilot.app.shared.widgets.PoseCard
import com.posepilot.app.shared.widgets.ScreenHeader
import com.posepilot.app.shared.widgets.SearchBar

@Composable
fun ExploreScreen(
    onOpenPose: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val favorites by FavoritesStore.favoriteIds.collectAsStateWithLifecycle()

    ExploreContent(
        state = state,
        favoriteIds = favorites,
        onOpenPose = onOpenPose,
        onQueryChange = viewModel::onQueryChange,
        onSelectCategory = viewModel::selectCategory,
        onToggleFavorite = FavoritesStore::toggle,
        modifier = modifier,
    )
}

@Composable
private fun ExploreContent(
    state: ExploreUiState,
    favoriteIds: Set<String>,
    onOpenPose: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    onSelectCategory: (String) -> Unit,
    onToggleFavorite: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_explore"),
        contentPadding = PaddingValues(
            start = AppSpacing.screenHorizontal,
            end = AppSpacing.screenHorizontal,
            top = AppSpacing.screenVertical,
            bottom = AppSpacing.sectionVertical,
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                ScreenHeader(
                    title = stringResource(R.string.explore_title),
                    subtitle = stringResource(R.string.explore_subtitle),
                )
                Spacer(modifier = Modifier.height(12.dp))
                SearchBar(
                    query = state.query,
                    onQueryChange = onQueryChange,
                )
                Spacer(modifier = Modifier.height(4.dp))
                CategoryRow(
                    selectedKey = state.categoryKey,
                    onSelect = onSelectCategory,
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        if (state.results.isEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                EmptyStateView(
                    title = stringResource(R.string.explore_empty_title),
                    body = stringResource(R.string.explore_empty_body),
                    icon = Icons.Filled.Search,
                    testTag = "explore_empty",
                )
            }
        } else {
            items(state.results, key = { it.id }) { pose ->
                PoseCard(
                    pose = pose,
                    onClick = { onOpenPose(pose.id) },
                    isFavorite = pose.id in favoriteIds,
                    onToggleFavorite = { onToggleFavorite(pose.id) },
                )
            }
        }
    }
}

@Composable
private fun CategoryRow(
    selectedKey: String,
    onSelect: (String) -> Unit,
) {
    val entries = listOf("all") + PoseCategory.entries.map { it.key }
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(entries, key = { it }) { key ->
            CategoryChip(
                label = categoryLabel(key),
                selected = selectedKey == key,
                onClick = { onSelect(key) },
                testTag = "category_chip_$key",
            )
        }
    }
}

@Composable
private fun categoryLabel(key: String): String = when (key) {
    "all" -> stringResource(R.string.category_all)
    else -> stringResource(
        PoseCategory.entries.firstOrNull { it.key == key }?.labelRes ?: R.string.category_all,
    )
}

@Preview(name = "Explore", showBackground = true)
@Composable
private fun ExploreScreenPreview() {
    PosePilotTheme {
        ExploreContent(
            state = ExploreUiState(),
            favoriteIds = emptySet(),
            onOpenPose = {},
            onQueryChange = {},
            onSelectCategory = {},
            onToggleFavorite = {},
        )
    }
}
