package com.posepilot.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExploreTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun exploreShowsHeaderSearchAndPoseCards() {
        composeRule.onNodeWithTag("nav_explore").performClick()

        composeRule.onNodeWithTag("screen_explore").assertIsDisplayed()
        composeRule.onNodeWithTag("search_bar").assertIsDisplayed()
        composeRule.onNodeWithTag("category_chip_all").assertIsDisplayed()
        composeRule.onNodeWithTag("pose_card_casual_standing").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun searchFiltersPosesAndShowsEmptyState() {
        composeRule.onNodeWithTag("nav_explore").performClick()

        composeRule.onNodeWithTag("search_bar").performTextInput("walk")
        composeRule.onNodeWithTag("pose_card_walking_shot").performScrollTo().assertIsDisplayed()

        composeRule.onNodeWithTag("search_clear").performClick()
        composeRule.onNodeWithTag("search_bar").performTextInput("zzzzz")
        composeRule.onNodeWithTag("explore_empty").assertIsDisplayed()
    }

    @Test
    fun categoryChipFiltersPoses() {
        composeRule.onNodeWithTag("nav_explore").performClick()

        composeRule.onNodeWithTag("category_chip_sitting").performScrollTo().performClick()
        composeRule.onNodeWithTag("pose_card_relaxed_sitting").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun tappingPoseOpensDetailsAndTryThisPoseOpensCamera() {
        composeRule.onNodeWithTag("nav_explore").performClick()

        composeRule.onNodeWithTag("pose_card_casual_standing").performScrollTo().performClick()
        composeRule.onNodeWithTag("screen_pose_details").assertIsDisplayed()

        composeRule.onNodeWithTag("button_try_pose").performScrollTo().performClick()
        composeRule.onNodeWithTag("screen_camera").assertIsDisplayed()
    }
}
