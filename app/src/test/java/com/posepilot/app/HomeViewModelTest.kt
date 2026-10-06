package com.posepilot.app

import com.posepilot.app.feature.home.HomeViewModel
import com.posepilot.app.shared.model.PoseMode
import com.posepilot.app.shared.model.Session
import com.posepilot.app.shared.model.UserSettings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeViewModelTest {

    @Test
    fun `initial state has no best score and solo selected`() {
        val viewModel = HomeViewModel()
        val state = viewModel.uiState.value

        assertNull(state.bestScore)
        assertEquals(PoseMode.SOLO, state.selectedMode)
        assertTrue(state.recentSessions.isEmpty())
        assertNull(state.errorMessage)
    }

    @Test
    fun `selectMode updates selected mode`() {
        val viewModel = HomeViewModel()

        viewModel.selectMode(PoseMode.COUPLE)
        assertEquals(PoseMode.COUPLE, viewModel.uiState.value.selectedMode)

        viewModel.selectMode(PoseMode.GROUP)
        assertEquals(PoseMode.GROUP, viewModel.uiState.value.selectedMode)
    }

    @Test
    fun `clearError resets error message`() {
        val viewModel = HomeViewModel()
        viewModel.clearError()
        assertNull(viewModel.uiState.value.errorMessage)
    }

    @Test
    fun `session defaults have no score`() {
        val session = Session(
            id = "session-1",
            startedAtMillis = 0L,
            mode = PoseMode.SOLO,
        )
        assertNull(session.bestScore)
        assertTrue(session.photos.isEmpty())
    }

    @Test
    fun `user settings defaults are safe`() {
        val settings = UserSettings()
        assertEquals("", settings.name)
        assertTrue(settings.mirrorFrontCamera)
        assertTrue(settings.saveOriginalPhotos)
        assertTrue(settings.soundEffectsEnabled)
    }
}
