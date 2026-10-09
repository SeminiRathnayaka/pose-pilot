package com.posepilot.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RouletteTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun rouletteDrawsPoseAndCanRequestNext() {
        composeRule.onNodeWithTag("button_open_roulette").performScrollTo().performClick()
        composeRule.onNodeWithTag("screen_roulette").assertIsDisplayed()

        composeRule.onNodeWithTag("roulette_count_10").performClick()
        composeRule.onNodeWithTag("button_start_roulette").performScrollTo().performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag("roulette_result").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag("roulette_pose_name").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithTag("button_save_favorite").performScrollTo().assertIsDisplayed()

        composeRule.onNodeWithTag("button_next_pose").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag("roulette_result").fetchSemanticsNodes().isNotEmpty()
        }
    }
}
