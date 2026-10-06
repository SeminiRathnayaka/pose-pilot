package com.posepilot.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeScreenShowsModeCardsAndActions() {
        composeRule.onNodeWithTag("screen_home").assertIsDisplayed()
        composeRule.onNodeWithTag("mode_card_solo").assertIsDisplayed()
        composeRule.onNodeWithTag("mode_card_couple").assertIsDisplayed()
        composeRule.onNodeWithTag("mode_card_group").assertIsDisplayed()
        composeRule.onNodeWithText(
            composeRule.activity.getString(R.string.button_start_session),
        ).performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText(
            composeRule.activity.getString(R.string.button_browse_poses),
        ).performScrollTo().assertIsDisplayed()
    }

    @Test
    fun selectingModeAndStartingSessionOpensCamera() {
        composeRule.onNodeWithTag("mode_card_couple").performClick()
        composeRule.onNodeWithText(
            composeRule.activity.getString(R.string.button_start_session),
        ).performScrollTo().performClick()

        composeRule.onNodeWithTag("screen_camera").assertIsDisplayed()
        composeRule.onNodeWithText(
            composeRule.activity.getString(R.string.pose_mode_couple),
        ).assertIsDisplayed()
    }

    @Test
    fun bestScoreEmptyStateIsShownBeforeFirstSession() {
        composeRule.onNodeWithText(
            composeRule.activity.getString(R.string.home_best_score_empty),
        ).performScrollTo().assertIsDisplayed()
    }
}
