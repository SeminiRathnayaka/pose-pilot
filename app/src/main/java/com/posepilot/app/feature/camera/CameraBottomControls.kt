package com.posepilot.app.feature.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppColors

@Composable
internal fun CameraBottomControls(
    capturing: Boolean,
    expanded: Boolean,
    onToggleExpanded: () -> Unit,
    timerEnabled: Boolean,
    onToggleTimer: () -> Unit,
    gridEnabled: Boolean,
    onToggleGrid: () -> Unit,
    mirrorEnabled: Boolean,
    onToggleMirror: () -> Unit,
    autoCaptureEnabled: Boolean,
    onToggleAutoCapture: () -> Unit,
    onShutter: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (expanded) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("camera_controls_panel"),
                color = Color.Black.copy(alpha = 0.6f),
                contentColor = AppColors.White,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    CameraControlButton(
                        label = stringResource(R.string.control_timer),
                        selected = timerEnabled,
                        onClick = onToggleTimer,
                        icon = painterResource(R.drawable.ic_timer),
                    )
                    CameraControlButton(
                        label = stringResource(R.string.control_grid),
                        selected = gridEnabled,
                        onClick = onToggleGrid,
                        icon = painterResource(R.drawable.ic_grid),
                    )
                    CameraControlButton(
                        label = stringResource(R.string.control_mirror),
                        selected = mirrorEnabled,
                        onClick = onToggleMirror,
                        icon = painterResource(R.drawable.ic_mirror),
                    )
                    CameraControlButton(
                        label = stringResource(R.string.control_auto_capture),
                        selected = autoCaptureEnabled,
                        onClick = onToggleAutoCapture,
                        icon = painterResource(R.drawable.ic_focus),
                    )
                }
            }
        }

        IconButton(
            onClick = onToggleExpanded,
            modifier = Modifier.testTag("camera_controls_toggle"),
        ) {
            Icon(
                imageVector = if (expanded) {
                    Icons.Filled.KeyboardArrowDown
                } else {
                    Icons.Filled.KeyboardArrowUp
                },
                contentDescription = stringResource(R.string.camera_controls),
                tint = AppColors.LightGray,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(modifier = Modifier.size(76.dp), contentAlignment = Alignment.Center) {
                CameraControlButton(
                    label = stringResource(R.string.camera_gallery),
                    onClick = { },
                    icon = painterResource(R.drawable.ic_gallery),
                )
            }
            CameraShutterButton(
                onClick = onShutter,
                capturing = capturing,
            )
            Box(modifier = Modifier.size(76.dp), contentAlignment = Alignment.Center) {
                CameraControlButton(
                    label = stringResource(R.string.camera_switch),
                    onClick = { },
                    icon = painterResource(R.drawable.ic_switch_camera),
                )
            }
        }
    }
}
