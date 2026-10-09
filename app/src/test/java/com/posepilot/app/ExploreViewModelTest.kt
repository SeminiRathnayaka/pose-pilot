package com.posepilot.app

import com.posepilot.app.feature.explore.ExploreViewModel
import com.posepilot.app.shared.data.SamplePoses
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExploreViewModelTest {

    @Test
    fun `initial state shows all poses`() {
        val viewModel = ExploreViewModel()

        assertEquals(SamplePoses.all.size, viewModel.uiState.value.results.size)
        assertEquals("all", viewModel.uiState.value.categoryKey)
        assertEquals("", viewModel.uiState.value.query)
    }

    @Test
    fun `query filters results and clears the search`() {
        val viewModel = ExploreViewModel()

        viewModel.onQueryChange("walking")
        assertTrue(viewModel.uiState.value.results.any { it.id == "walking_shot" })
        assertTrue(viewModel.uiState.value.results.none { it.id == "cafe_lean" })

        viewModel.clearSearch()
        assertEquals(SamplePoses.all.size, viewModel.uiState.value.results.size)
    }

    @Test
    fun `category selection filters results`() {
        val viewModel = ExploreViewModel()

        viewModel.selectCategory("fashion")
        assertTrue(viewModel.uiState.value.results.isNotEmpty())
        assertTrue(viewModel.uiState.value.results.all { it.category.key == "fashion" })
    }

    @Test
    fun `query and category combine to an empty result`() {
        val viewModel = ExploreViewModel()

        viewModel.selectCategory("graduation")
        viewModel.onQueryChange("walking")
        assertTrue(viewModel.uiState.value.results.isEmpty())
    }
}
