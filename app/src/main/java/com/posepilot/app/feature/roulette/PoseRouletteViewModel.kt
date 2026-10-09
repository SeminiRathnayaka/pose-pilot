package com.posepilot.app.feature.roulette

import androidx.lifecycle.ViewModel
import com.posepilot.app.shared.data.SamplePoses
import com.posepilot.app.shared.model.Pose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RouletteUiState(
    val posesPerRound: Int = 5,
    val isDrawing: Boolean = false,
    val currentPose: Pose? = null,
    val drawnIds: List<String> = emptyList(),
    val roundNumber: Int = 0,
)

class RouletteViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RouletteUiState())
    val uiState: StateFlow<RouletteUiState> = _uiState.asStateFlow()

    fun selectPosesPerRound(count: Int) {
        _uiState.update {
            it.copy(
                posesPerRound = count,
                drawnIds = emptyList(),
                roundNumber = 0,
            )
        }
    }

    fun startDraw() {
        _uiState.update { it.copy(isDrawing = true) }
    }

    fun finishDraw(random: (RouletteUiState) -> Pose? = ::defaultRandom) {
        _uiState.update { state ->
            val pose = random(state)
            if (pose == null) {
                state.copy(isDrawing = false)
            } else {
                val nextDrawn = state.drawnIds + pose.id
                val roundComplete = nextDrawn.size >= state.posesPerRound
                state.copy(
                    isDrawing = false,
                    currentPose = pose,
                    drawnIds = if (roundComplete) emptyList() else nextDrawn,
                    roundNumber = if (roundComplete) state.roundNumber + 1 else state.roundNumber,
                )
            }
        }
    }

    fun nextPose(random: (RouletteUiState) -> Pose? = ::defaultRandom) {
        startDraw()
        finishDraw(random)
    }

    fun clearPose() {
        _uiState.update { it.copy(currentPose = null) }
    }

    private fun defaultRandom(state: RouletteUiState): Pose? {
        val candidates = SamplePoses.all.filterNot { it.id in state.drawnIds }
        return candidates.randomOrNull()
    }
}
