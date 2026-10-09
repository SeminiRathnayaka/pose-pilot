package com.posepilot.app.feature.explore

import androidx.lifecycle.ViewModel
import com.posepilot.app.shared.data.SamplePoses
import com.posepilot.app.shared.model.Pose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ExploreUiState(
    val query: String = "",
    val categoryKey: String = "all",
    val results: List<Pose> = SamplePoses.all,
)

class ExploreViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    fun onQueryChange(query: String) {
        _uiState.update { state ->
            val newState = state.copy(query = query)
            newState.copy(results = computeResults(newState))
        }
    }

    fun selectCategory(categoryKey: String) {
        _uiState.update { state ->
            val newState = state.copy(categoryKey = categoryKey)
            newState.copy(results = computeResults(newState))
        }
    }

    fun clearSearch() {
        onQueryChange("")
    }

    private fun computeResults(state: ExploreUiState): List<Pose> =
        SamplePoses.search(query = state.query, categoryKey = state.categoryKey)
}
