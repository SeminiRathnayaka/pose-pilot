package com.posepilot.app.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppCardStyles
import com.posepilot.app.core.theme.AppColors
import com.posepilot.app.core.theme.AppRadius
import com.posepilot.app.core.theme.AppSpacing
import com.posepilot.app.core.theme.PosePilotTheme
import com.posepilot.app.shared.data.FavoritesStore
import com.posepilot.app.shared.data.SamplePoses
import com.posepilot.app.shared.model.PoseMode
import com.posepilot.app.shared.widgets.EmptyStateView
import com.posepilot.app.shared.widgets.ModeCard
import com.posepilot.app.shared.widgets.PoseCompactCard
import com.posepilot.app.shared.widgets.PrimaryButton
import com.posepilot.app.shared.widgets.SecondaryButton
import com.posepilot.app.shared.widgets.SectionHeader

@Composable
fun HomeScreen(
    onStartShooting: (PoseMode) -> Unit,
    onExplorePoses: () -> Unit,
    onOpenRoulette: () -> Unit,
    onOpenPose: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val favorites by FavoritesStore.favoriteIds.collectAsStateWithLifecycle()

    HomeContent(
        state = state,
        favoriteIds = favorites,
        onStartShooting = onStartShooting,
        onExplorePoses = onExplorePoses,
        onOpenRoulette = onOpenRoulette,
        onOpenPose = onOpenPose,
        onSelectMode = viewModel::selectMode,
        onToggleFavorite = FavoritesStore::toggle,
        modifier = modifier,
    )
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    favoriteIds: Set<String>,
    onStartShooting: (PoseMode) -> Unit,
    onExplorePoses: () -> Unit,
    onOpenRoulette: () -> Unit,
    onOpenPose: (String) -> Unit,
    onSelectMode: (PoseMode) -> Unit,
    onToggleFavorite: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_home")
            .verticalScroll(rememberScrollState())
            .padding(horizontal = AppSpacing.screenHorizontal, vertical = AppSpacing.screenVertical),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.sectionVertical),
    ) {
        HomeHeader()

        HeroCard(
            onStartShooting = { onStartShooting(state.selectedMode) },
            onExplorePoses = onExplorePoses,
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionHeader(title = stringResource(R.string.section_modes))
            ModeGrid(
                selectedMode = state.selectedMode,
                onSelectMode = onSelectMode,
            )
        }

        PopularPosesSection(
            favoriteIds = favoriteIds,
            onOpenPose = onOpenPose,
            onToggleFavorite = onToggleFavorite,
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionHeader(title = stringResource(R.string.section_recent))
            EmptyStateView(
                title = stringResource(R.string.home_recent_empty),
                testTag = "recent_empty",
            )
        }

        FavoritesSection(
            favoriteIds = favoriteIds,
            onOpenPose = onOpenPose,
            onToggleFavorite = onToggleFavorite,
        )

        RouletteCard(onOpenRoulette = onOpenRoulette)

        TipCard()
    }
}

@Composable
private fun HomeHeader() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.labelLarge,
            color = AppColors.Gray,
        )
        Text(
            text = stringResource(R.string.home_welcome),
            style = MaterialTheme.typography.headlineSmall,
            color = AppColors.White,
        )
        Text(
            text = stringResource(R.string.home_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = AppColors.LightGray,
        )
    }
}

@Composable
private fun HeroCard(
    onStartShooting: () -> Unit,
    onExplorePoses: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_hero"),
        shape = RoundedCornerShape(AppRadius.xl),
        color = AppColors.Navy,
        contentColor = AppColors.White,
        border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(R.string.hero_title),
                style = MaterialTheme.typography.titleLarge,
                color = AppColors.White,
            )
            Text(
                text = stringResource(R.string.hero_desc),
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.LightGray,
            )
            PrimaryButton(
                text = stringResource(R.string.button_start_shooting),
                onClick = onStartShooting,
                modifier = Modifier.testTag("button_start_shooting"),
            )
            SecondaryButton(
                text = stringResource(R.string.button_explore_poses),
                onClick = onExplorePoses,
                modifier = Modifier.testTag("button_explore_poses"),
            )
        }
    }
}

@Composable
private fun ModeGrid(
    selectedMode: PoseMode,
    onSelectMode: (PoseMode) -> Unit,
) {
    BoxWithConstraints {
        val columns = if (maxWidth >= 600.dp) 4 else 2
        val rows = PoseMode.entries.chunked(columns)
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            rows.forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    row.forEach { mode ->
                        ModeCard(
                            mode = mode,
                            icon = modeIcon(mode),
                            onClick = { onSelectMode(mode) },
                            emphasized = selectedMode == mode,
                            modifier = Modifier.weight(1f),
                        )
                    }
                    if (row.size < columns) {
                        repeat(columns - row.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PopularPosesSection(
    favoriteIds: Set<String>,
    onOpenPose: (String) -> Unit,
    onToggleFavorite: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = stringResource(R.string.section_popular))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(SamplePoses.popular, key = { it.id }) { pose ->
                PoseCompactCard(
                    pose = pose,
                    onClick = { onOpenPose(pose.id) },
                    isFavorite = pose.id in favoriteIds,
                    onToggleFavorite = { onToggleFavorite(pose.id) },
                    modifier = Modifier.width(260.dp),
                )
            }
        }
    }
}

@Composable
private fun FavoritesSection(
    favoriteIds: Set<String>,
    onOpenPose: (String) -> Unit,
    onToggleFavorite: (String) -> Unit,
) {
    val favoritePoses = SamplePoses.all.filter { it.id in favoriteIds }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = stringResource(R.string.section_favorites))
        if (favoritePoses.isEmpty()) {
            EmptyStateView(
                title = stringResource(R.string.home_favorites_empty),
                icon = Icons.Filled.FavoriteBorder,
                testTag = "favorites_empty",
            )
        } else {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(favoritePoses, key = { it.id }) { pose ->
                    PoseCompactCard(
                        pose = pose,
                        onClick = { onOpenPose(pose.id) },
                        isFavorite = true,
                        onToggleFavorite = { onToggleFavorite(pose.id) },
                        modifier = Modifier.width(260.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun RouletteCard(onOpenRoulette: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_roulette"),
        shape = AppCardStyles.shape,
        color = AppColors.DarkGray,
        contentColor = AppColors.White,
        border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_dice),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = AppColors.LightGray,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.section_roulette),
                    style = MaterialTheme.typography.titleMedium,
                    color = AppColors.White,
                )
                Text(
                    text = stringResource(R.string.home_roulette_body),
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.LightGray,
                )
            }
            SecondaryButton(
                text = stringResource(R.string.home_roulette_action),
                onClick = onOpenRoulette,
                modifier = Modifier
                    .width(148.dp)
                    .testTag("button_open_roulette"),
            )
        }
    }
}

@Composable
private fun TipCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_tip"),
        shape = AppCardStyles.shape,
        color = AppColors.Black,
        contentColor = AppColors.White,
        border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = stringResource(R.string.section_tip),
                style = MaterialTheme.typography.labelSmall,
                color = AppColors.Gray,
            )
            Text(
                text = stringResource(R.string.tip_next_title),
                style = MaterialTheme.typography.titleSmall,
                color = AppColors.White,
            )
            Text(
                text = stringResource(R.string.tip_next_body),
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.LightGray,
            )
        }
    }
}

@Composable
private fun modeIcon(mode: PoseMode): Painter = when (mode) {
    PoseMode.SOLO -> painterResource(R.drawable.ic_poses)
    PoseMode.COUPLE -> painterResource(R.drawable.ic_couple)
    PoseMode.GROUP -> painterResource(R.drawable.ic_group)
    PoseMode.FREE -> painterResource(R.drawable.ic_free)
}

@Preview(name = "Home", showBackground = true)
@Composable
private fun HomeScreenPreview() {
    PosePilotTheme {
        HomeContent(
            state = HomeUiState(),
            favoriteIds = emptySet(),
            onStartShooting = {},
            onExplorePoses = {},
            onOpenRoulette = {},
            onOpenPose = {},
            onSelectMode = {},
            onToggleFavorite = {},
        )
    }
}
