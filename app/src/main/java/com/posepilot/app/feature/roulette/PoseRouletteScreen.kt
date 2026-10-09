package com.posepilot.app.feature.roulette

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppCardStyles
import com.posepilot.app.core.theme.AppColors
import com.posepilot.app.core.theme.AppSpacing
import com.posepilot.app.core.theme.PosePilotTheme
import com.posepilot.app.shared.data.FavoritesStore
import com.posepilot.app.shared.data.SamplePoses
import com.posepilot.app.shared.model.Pose
import com.posepilot.app.shared.widgets.EmptyStateView
import com.posepilot.app.shared.widgets.LoadingPlaceholder
import com.posepilot.app.shared.widgets.PoseThumbnail
import com.posepilot.app.shared.widgets.PosePilotTopBar
import com.posepilot.app.shared.widgets.PrimaryButton
import com.posepilot.app.shared.widgets.SecondaryButton

@Composable
fun PoseRouletteScreen(
    onBack: () -> Unit,
    onOpenPose: (String) -> Unit,
    onTryPose: (Pose) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RouletteViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val favorites by FavoritesStore.favoriteIds.collectAsStateWithLifecycle()

    LaunchedEffect(state.isDrawing) {
        if (state.isDrawing) {
            kotlinx.coroutines.delay(900)
            viewModel.finishDraw()
        }
    }

    RouletteContent(
        state = state,
        favoriteIds = favorites,
        onBack = onBack,
        onOpenPose = onOpenPose,
        onTryPose = onTryPose,
        onSelectCount = viewModel::selectPosesPerRound,
        onStartRoulette = viewModel::startDraw,
        onNextPose = viewModel::nextPose,
        onToggleFavorite = FavoritesStore::toggle,
        modifier = modifier,
    )
}

@Composable
private fun RouletteContent(
    state: RouletteUiState,
    favoriteIds: Set<String>,
    onBack: () -> Unit,
    onOpenPose: (String) -> Unit,
    onTryPose: (Pose) -> Unit,
    onSelectCount: (Int) -> Unit,
    onStartRoulette: () -> Unit,
    onNextPose: () -> Unit,
    onToggleFavorite: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_roulette")
            .verticalScroll(rememberScrollState()),
    ) {
        PosePilotTopBar(
            title = stringResource(R.string.roulette_title),
            showBack = true,
            onBack = onBack,
        )

        if (SamplePoses.all.isEmpty()) {
            EmptyStateView(
                title = stringResource(R.string.roulette_empty),
                modifier = Modifier.padding(AppSpacing.screenHorizontal),
                testTag = "roulette_empty",
            )
            return@Column
        }

        Column(
            modifier = Modifier.padding(
                start = AppSpacing.screenHorizontal,
                end = AppSpacing.screenHorizontal,
                bottom = AppSpacing.sectionVertical,
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.roulette_desc),
                style = MaterialTheme.typography.bodyLarge,
                color = AppColors.LightGray,
            )

            Text(
                text = stringResource(R.string.roulette_count_label),
                style = MaterialTheme.typography.labelLarge,
                color = AppColors.Gray,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(5, 10, 20).forEach { count ->
                    CountCard(
                        count = count,
                        selected = state.posesPerRound == count,
                        onClick = { onSelectCount(count) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            PrimaryButton(
                text = stringResource(R.string.button_start_roulette),
                onClick = onStartRoulette,
                modifier = Modifier.testTag("button_start_roulette"),
            )

            when {
                state.isDrawing -> {
                    LoadingPlaceholder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .testTag("roulette_drawing"),
                    )
                    Text(
                        text = stringResource(R.string.roulette_drawing),
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppColors.Gray,
                        modifier = Modifier.testTag("roulette_drawing_label"),
                    )
                }

                state.currentPose != null -> {
                    DrawnPoseCard(
                        pose = state.currentPose,
                        isFavorite = state.currentPose.id in favoriteIds,
                        onOpenPose = { onOpenPose(state.currentPose.id) },
                        onTryPose = { onTryPose(state.currentPose) },
                        onNextPose = onNextPose,
                        onToggleFavorite = { onToggleFavorite(state.currentPose.id) },
                    )
                }

                else -> {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("roulette_idle"),
                        shape = AppCardStyles.shape,
                        color = AppColors.Black,
                        contentColor = AppColors.LightGray,
                        border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = stringResource(R.string.roulette_desc),
                                style = MaterialTheme.typography.bodyMedium,
                                color = AppColors.LightGray,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CountCard(
    count: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.testTag("roulette_count_$count"),
        shape = AppCardStyles.shape,
        color = if (selected) AppColors.White else AppColors.Navy,
        contentColor = if (selected) AppColors.DeepNavy else AppColors.White,
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) AppColors.White else AppColors.FaintBorder,
        ),
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Composable
private fun DrawnPoseCard(
    pose: Pose,
    isFavorite: Boolean,
    onOpenPose: () -> Unit,
    onTryPose: () -> Unit,
    onNextPose: () -> Unit,
    onToggleFavorite: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("roulette_result"),
        shape = AppCardStyles.shape,
        color = AppColors.Navy,
        contentColor = AppColors.White,
        border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Surface(
                onClick = onOpenPose,
                shape = AppCardStyles.shape,
                color = Color.Transparent,
            ) {
                PoseThumbnail(
                    pose = pose,
                    large = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 3f),
                )
            }
            Text(
                text = pose.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.testTag("roulette_pose_name"),
            )
            Text(
                text = "${stringResource(pose.category.labelRes)} · ${stringResource(pose.difficulty.labelRes)}",
                style = MaterialTheme.typography.labelMedium,
                color = AppColors.Gray,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                PrimaryButton(
                    text = stringResource(R.string.action_try_pose),
                    onClick = onTryPose,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("button_try_pose"),
                )
                SecondaryButton(
                    text = stringResource(R.string.action_next_pose),
                    onClick = onNextPose,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("button_next_pose"),
                )
            }
            SecondaryButton(
                text = stringResource(
                    if (isFavorite) R.string.action_saved_favorite else R.string.action_save_favorite,
                ),
                onClick = onToggleFavorite,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("button_save_favorite"),
            )
        }
    }
}

@Preview(name = "Roulette", showBackground = true)
@Composable
private fun RoulettePreview() {
    PosePilotTheme {
        RouletteContent(
            state = RouletteUiState(),
            favoriteIds = emptySet(),
            onBack = {},
            onOpenPose = {},
            onTryPose = {},
            onSelectCount = {},
            onStartRoulette = {},
            onNextPose = {},
            onToggleFavorite = {},
        )
    }
}
