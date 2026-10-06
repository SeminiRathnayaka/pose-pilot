package com.posepilot.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun bottomNavOpensPosesScreen() {
        composeRule.onNodeWithTag("nav_poses").performClick()
        composeRule.onNodeWithTag("screen_poses").assertIsDisplayed()
    }

    @Test
    fun bottomNavOpensHistoryScreen() {
        composeRule.onNodeWithTag("nav_history").performClick()
        composeRule.onNodeWithTag("screen_history").assertIsDisplayed()
    }

    @Test
    fun bottomNavOpensProfileScreen() {
        composeRule.onNodeWithTag("nav_profile").performClick()
        composeRule.onNodeWithTag("screen_profile").assertIsDisplayed()
    }

    @Test
    fun bottomNavReturnsToHomeFromCamera() {
        composeRule.onNodeWithTag("nav_camera").performClick()
        composeRule.onNodeWithTag("screen_camera").assertIsDisplayed()

        composeRule.onNodeWithTag("nav_home").performClick()
        composeRule.onNodeWithTag("screen_home").assertIsDisplayed()
    }
}
