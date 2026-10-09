package com.posepilot.app

import com.posepilot.app.feature.roulette.RouletteViewModel
import com.posepilot.app.shared.data.SamplePoses
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RouletteViewModelTest {

    @Test
    fun `initial state is idle with five poses per round`() {
        val viewModel = RouletteViewModel()

        assertEquals(5, viewModel.uiState.value.posesPerRound)
        assertFalse(viewModel.uiState.value.isDrawing)
        assertEquals(null, viewModel.uiState.value.currentPose)
    }

    @Test
    fun `startDraw marks the state as drawing`() {
        val viewModel = RouletteViewModel()

        viewModel.startDraw()
        assertTrue(viewModel.uiState.value.isDrawing)
    }

    @Test
    fun `finishDraw selects the pose provided by the random source`() {
        val viewModel = RouletteViewModel()
        val expected = SamplePoses.byId("street_style")!!

        viewModel.startDraw()
        viewModel.finishDraw { expected }

        val state = viewModel.uiState.value
        assertFalse(state.isDrawing)
        assertEquals(expected, state.currentPose)
        assertEquals(listOf("street_style"), state.drawnIds)
    }

    @Test
    fun `finishDraw restarts the round after reaching the pose count`() {
        val viewModel = RouletteViewModel()
        viewModel.selectPosesPerRound(2)
        val first = SamplePoses.all[0]
        val second = SamplePoses.all[1]

        viewModel.finishDraw { first }
        viewModel.finishDraw { second }

        val state = viewModel.uiState.value
        assertTrue(state.drawnIds.isEmpty())
        assertEquals(1, state.roundNumber)
        assertNotNull(state.currentPose)
    }

    @Test
    fun `selectPosesPerRound resets the current round`() {
        val viewModel = RouletteViewModel()
        viewModel.finishDraw { SamplePoses.all[0] }

        viewModel.selectPosesPerRound(10)

        val state = viewModel.uiState.value
        assertEquals(10, state.posesPerRound)
        assertTrue(state.drawnIds.isEmpty())
        assertEquals(0, state.roundNumber)
    }
}
