package com.posepilot.app.feature.home

import androidx.lifecycle.ViewModel
import com.posepilot.app.shared.model.PoseMode
import com.posepilot.app.shared.model.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeUiState(
    val selectedMode: PoseMode = PoseMode.SOLO,
    val bestScore: Int? = null,
    val recentSessions: List<Session> = emptyList(),
    val errorMessage: String? = null,
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun selectMode(mode: PoseMode) {
        _uiState.update { it.copy(selectedMode = mode) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
