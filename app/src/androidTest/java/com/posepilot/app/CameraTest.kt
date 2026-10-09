package com.posepilot.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CameraTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun cameraFlowCoversPermissionPreviewStatesAndControls() {
        composeRule.onNodeWithTag("button_start_shooting").performScrollTo().performClick()
        composeRule.onNodeWithTag("screen_camera").assertIsDisplayed()

        composeRule.onNodeWithTag("camera_permission_title").assertIsDisplayed()
        composeRule.onNodeWithTag("button_allow_camera").performClick()

        composeRule.onNodeWithTag("camera_preview").assertIsDisplayed()
        composeRule.onNodeWithTag("camera_status_pill").assertIsDisplayed()
        composeRule.onNodeWithTag("camera_shutter").assertIsDisplayed()

        composeRule.onNodeWithTag("camera_controls_toggle").performClick()
        composeRule.onNodeWithTag("camera_controls_panel").assertIsDisplayed()
        composeRule.onNodeWithTag("camera_controls_toggle").performClick()

        composeRule.onNodeWithTag("camera_more").performClick()
        composeRule.onNodeWithTag("camera_state_error").performClick()
        composeRule.onNodeWithTag("camera_error").assertIsDisplayed()

        composeRule.onNodeWithTag("error_primary").performClick()
        composeRule.onNodeWithTag("camera_preview").assertIsDisplayed()
    }
}
