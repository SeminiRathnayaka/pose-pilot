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
    fun homeScreenShowsModeCardsAndHeroActions() {
        composeRule.onNodeWithTag("screen_home").assertIsDisplayed()
        composeRule.onNodeWithTag("mode_card_solo").assertIsDisplayed()
        composeRule.onNodeWithTag("mode_card_couple").assertIsDisplayed()
        composeRule.onNodeWithTag("mode_card_group").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithTag("mode_card_free").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithTag("button_start_shooting").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithTag("button_explore_poses").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun selectingModeAndStartingShootingOpensCamera() {
        composeRule.onNodeWithTag("mode_card_couple").performClick()
        composeRule.onNodeWithTag("button_start_shooting").performScrollTo().performClick()

        composeRule.onNodeWithTag("screen_camera").assertIsDisplayed()
        composeRule.onNodeWithText(
            composeRule.activity.getString(R.string.pose_mode_couple),
        ).assertIsDisplayed()
    }

    @Test
    fun homeShowsEmptyStatesBeforeFirstUse() {
        composeRule.onNodeWithTag("recent_empty").performScrollTo().assertIsDisplayed()
    }
}
