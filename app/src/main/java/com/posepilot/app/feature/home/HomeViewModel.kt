package com.posepilot.app.feature.home

import androidx.lifecycle.ViewModel
import com.posepilot.app.shared.model.PoseMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeUiState(
    val selectedMode: PoseMode = PoseMode.SOLO,
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun selectMode(mode: PoseMode) {
        _uiState.update { it.copy(selectedMode = mode) }
    }
}
