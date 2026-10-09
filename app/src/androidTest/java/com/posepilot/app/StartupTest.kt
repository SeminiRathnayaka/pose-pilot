package com.posepilot.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartupTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appLaunchesAndShowsHomeScreen() {
        composeRule.onNodeWithTag("screen_home").assertIsDisplayed()
        composeRule.onNodeWithText(
            composeRule.activity.getString(R.string.home_welcome),
        ).assertIsDisplayed()
    }

    @Test
    fun allBottomNavItemsAreVisible() {
        listOf("nav_home", "nav_explore", "nav_camera", "nav_history", "nav_profile").forEach { tag ->
            composeRule.onNodeWithTag(tag).assertIsDisplayed()
        }
    }
}
