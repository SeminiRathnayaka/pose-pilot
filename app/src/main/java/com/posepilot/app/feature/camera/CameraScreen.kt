package com.posepilot.app.feature.camera

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.posepilot.app.R
import com.posepilot.app.core.theme.PosePilotTheme
import com.posepilot.app.shared.model.PoseMode
import com.posepilot.app.shared.widgets.PlaceholderContent
import com.posepilot.app.shared.widgets.ScreenHeader

@Composable
fun CameraScreen(
    poseMode: PoseMode,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
    ) {
        ScreenHeader(
            title = stringResource(poseMode.labelRes),
            subtitle = stringResource(R.string.nav_camera),
        )
        PlaceholderContent(
            title = stringResource(R.string.placeholder_camera_title),
            body = stringResource(R.string.placeholder_camera_body),
            testTag = "screen_camera",
        )
    }
}

@Preview(name = "Camera light", showBackground = true)
@Composable
private fun CameraScreenPreview() {
    PosePilotTheme {
        CameraScreen(poseMode = PoseMode.SOLO)
    }
}
