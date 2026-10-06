package com.posepilot.app.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.posepilot.app.core.theme.PosePilotTheme
import com.posepilot.app.shared.model.PoseMode
import com.posepilot.app.shared.widgets.EmptyState
import com.posepilot.app.shared.widgets.PoseModeCard
import com.posepilot.app.shared.widgets.PrimaryButton
import com.posepilot.app.shared.widgets.ScoreCard
import com.posepilot.app.shared.widgets.ScreenHeader
import com.posepilot.app.shared.widgets.SecondaryButton
import com.posepilot.app.shared.widgets.SectionHeader

@Composable
fun HomeScreen(
    onStartSession: (PoseMode) -> Unit,
    onBrowsePoses: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        state = state,
        onStartSession = onStartSession,
        onBrowsePoses = onBrowsePoses,
        onSelectMode = viewModel::selectMode,
        modifier = modifier,
    )
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    onStartSession: (PoseMode) -> Unit,
    onBrowsePoses: () -> Unit,
    onSelectMode: (PoseMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_home")
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        ScreenHeader(
            title = stringResource(R.string.home_greeting),
            subtitle = stringResource(R.string.app_tagline),
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionHeader(title = stringResource(R.string.home_choose_mode))
            BoxWithConstraints {
                if (maxWidth >= 600.dp) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        PoseMode.entries.forEach { mode ->
                            ModeCardSlot(
                                mode = mode,
                                isSelected = state.selectedMode == mode,
                                onSelect = onSelectMode,
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        PoseMode.entries.forEach { mode ->
                            ModeCardSlot(
                                mode = mode,
                                isSelected = state.selectedMode == mode,
                                onSelect = onSelectMode,
                            )
                        }
                    }
                }
            }
        }

        ScoreCard(score = state.bestScore)

        state.errorMessage?.let { message ->
            EmptyState(title = message)
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            PrimaryButton(
                text = stringResource(R.string.button_start_session),
                onClick = { onStartSession(state.selectedMode) },
            )
            SecondaryButton(
                text = stringResource(R.string.button_browse_poses),
                onClick = onBrowsePoses,
            )
            Text(
                text = stringResource(R.string.home_camera_note),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun ModeCardSlot(
    mode: PoseMode,
    isSelected: Boolean,
    onSelect: (PoseMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    PoseModeCard(
        mode = mode,
        icon = modeIcon(mode),
        onClick = { onSelect(mode) },
        modifier = modifier.testTag("mode_card_${mode.key}"),
    )
}

@Composable
private fun modeIcon(mode: PoseMode): Painter? = when (mode) {
    PoseMode.SOLO -> painterResource(id = R.drawable.ic_poses)
    PoseMode.COUPLE -> painterResource(id = R.drawable.ic_couple)
    PoseMode.GROUP -> painterResource(id = R.drawable.ic_group)
}

@Preview(name = "Home light", showBackground = true)
@Composable
private fun HomeScreenPreview() {
    PosePilotTheme {
        HomeContent(
            state = HomeUiState(),
            onStartSession = {},
            onBrowsePoses = {},
            onSelectMode = {},
        )
    }
}
