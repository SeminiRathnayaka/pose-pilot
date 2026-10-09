package com.posepilot.app.feature.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppColors
import com.posepilot.app.core.theme.PosePilotTheme
import com.posepilot.app.shared.data.SamplePoses
import com.posepilot.app.shared.model.PoseMode
import com.posepilot.app.shared.widgets.ErrorStateView
import com.posepilot.app.shared.widgets.PrimaryButton
import com.posepilot.app.shared.widgets.SecondaryButton

object CameraFlowState {
    var permissionAcknowledged: Boolean = false
}

enum class CameraUiState {
    Permission,
    Preview,
    Countdown,
    Capturing,
    PoseReady,
    Error,
}

@Composable
fun CameraScreen(
    poseMode: PoseMode,
    refPoseId: String,
    onBack: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var state by rememberSaveable { mutableStateOf(resolveInitialCameraState()) }
    var flashEnabled by rememberSaveable { mutableStateOf(false) }
    var gridEnabled by rememberSaveable { mutableStateOf(true) }
    var mirrorEnabled by rememberSaveable { mutableStateOf(false) }
    var timerEnabled by rememberSaveable { mutableStateOf(false) }
    var autoCaptureEnabled by rememberSaveable { mutableStateOf(false) }
    var controlsExpanded by rememberSaveable { mutableStateOf(false) }

    val refPose = refPoseId.takeIf { it.isNotBlank() }?.let { SamplePoses.byId(it) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppColors.Black)
            .testTag("screen_camera"),
    ) {
        CameraPreviewPlaceholder(
            showGrid = gridEnabled && state != CameraUiState.Permission && state != CameraUiState.Error,
            modifier = Modifier.fillMaxSize(),
        ) {
            when (state) {
                CameraUiState.Permission -> Unit
                CameraUiState.Error -> Unit
                CameraUiState.Countdown -> CameraCountdownOverlay()
                CameraUiState.Capturing -> CameraCapturingOverlay()
                CameraUiState.PoseReady -> CameraPoseReadyOverlay()
                CameraUiState.Preview -> CameraStatusPill()
            }
        }

        if (refPose != null &&
            state != CameraUiState.Permission &&
            state != CameraUiState.Error
        ) {
            CameraReferenceThumbnail(
                poseName = refPose.name,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 72.dp, start = 16.dp),
            )
        }

        CameraTopBar(
            poseMode = poseMode,
            flashEnabled = flashEnabled,
            onBack = onBack,
            onToggleFlash = { flashEnabled = !flashEnabled },
            onOpenSettings = onOpenSettings,
            onPreviewState = { state = it },
            modifier = Modifier.align(Alignment.TopCenter),
        )

        if (state != CameraUiState.Permission && state != CameraUiState.Error) {
            CameraBottomControls(
                capturing = state == CameraUiState.Capturing,
                expanded = controlsExpanded,
                onToggleExpanded = { controlsExpanded = !controlsExpanded },
                timerEnabled = timerEnabled,
                onToggleTimer = { timerEnabled = !timerEnabled },
                gridEnabled = gridEnabled,
                onToggleGrid = { gridEnabled = !gridEnabled },
                mirrorEnabled = mirrorEnabled,
                onToggleMirror = { mirrorEnabled = !mirrorEnabled },
                autoCaptureEnabled = autoCaptureEnabled,
                onToggleAutoCapture = { autoCaptureEnabled = !autoCaptureEnabled },
                onShutter = { state = CameraUiState.Countdown },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }

        when (state) {
            CameraUiState.Permission -> {
                CameraPermissionState(
                    onAllow = {
                        CameraFlowState.permissionAcknowledged = true
                        state = CameraUiState.Preview
                    },
                    onNotNow = onBack,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            CameraUiState.Error -> {
                ErrorStateView(
                    title = stringResource(R.string.camera_error_title),
                    body = stringResource(R.string.camera_error_body),
                    primaryText = stringResource(R.string.button_try_again),
                    onPrimary = { state = CameraUiState.Preview },
                    secondaryText = stringResource(R.string.button_go_back),
                    onSecondary = onBack,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = AppColors.Black)
                        .padding(top = 96.dp),
                    testTag = "camera_error",
                )
            }

            else -> Unit
        }
    }
}

private fun resolveInitialCameraState(): CameraUiState =
    if (CameraFlowState.permissionAcknowledged) CameraUiState.Preview else CameraUiState.Permission

@Composable
private fun CameraTopBar(
    poseMode: PoseMode,
    flashEnabled: Boolean,
    onBack: () -> Unit,
    onToggleFlash: () -> Unit,
    onOpenSettings: () -> Unit,
    onPreviewState: (CameraUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    var menuOpen by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Black.copy(alpha = 0.45f))
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .testTag("camera_top_bar"),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBack, modifier = Modifier.testTag("camera_back")) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.action_back),
                tint = AppColors.White,
            )
        }
        Text(
            text = stringResource(poseMode.labelRes),
            style = MaterialTheme.typography.labelLarge,
            color = AppColors.White,
            modifier = Modifier
                .weight(1f)
                .testTag("camera_mode_label"),
        )
        IconButton(onClick = onToggleFlash, modifier = Modifier.testTag("camera_flash")) {
            Icon(
                painter = painterResource(
                    if (flashEnabled) R.drawable.ic_flash_off else R.drawable.ic_flash,
                ),
                contentDescription = stringResource(R.string.camera_flash),
                tint = if (flashEnabled) AppColors.LightGray else AppColors.White,
            )
        }
        Box {
            IconButton(onClick = { menuOpen = true }, modifier = Modifier.testTag("camera_more")) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(R.string.camera_more),
                    tint = AppColors.White,
                )
            }
            CameraStateMenu(
                expanded = menuOpen,
                onDismiss = { menuOpen = false },
                onPreviewState = {
                    menuOpen = false
                    onPreviewState(it)
                },
                onOpenSettings = {
                    menuOpen = false
                    onOpenSettings()
                },
            )
        }
    }
}

@Composable
private fun CameraStateMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onPreviewState: (CameraUiState) -> Unit,
    onOpenSettings: () -> Unit,
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismiss) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.camera_state_menu),
                    color = AppColors.Gray,
                    style = MaterialTheme.typography.labelLarge,
                )
            },
            onClick = onDismiss,
            enabled = false,
        )
        HorizontalDivider(color = AppColors.FaintBorder)
        CameraUiState.entries.forEach { uiState ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(stateLabel(uiState)),
                            color = AppColors.White,
                        )
                    },
                    onClick = { onPreviewState(uiState) },
                    modifier = Modifier.testTag(stateTestTag(uiState)),
                )
            }
        HorizontalDivider(color = AppColors.FaintBorder)
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.camera_settings_entry),
                    color = AppColors.White,
                )
            },
            onClick = onOpenSettings,
            modifier = Modifier.testTag("camera_menu_settings"),
        )
    }
}

private fun stateLabel(state: CameraUiState): Int = when (state) {
    CameraUiState.Permission -> R.string.camera_state_permission
    CameraUiState.Preview -> R.string.camera_status_preview
    CameraUiState.Countdown -> R.string.camera_state_countdown
    CameraUiState.Capturing -> R.string.camera_state_capturing
    CameraUiState.PoseReady -> R.string.camera_state_pose_ready
    CameraUiState.Error -> R.string.camera_state_error
}

private fun stateTestTag(state: CameraUiState): String = when (state) {
    CameraUiState.Permission -> "camera_state_permission"
    CameraUiState.Preview -> "camera_state_preview"
    CameraUiState.Countdown -> "camera_state_countdown"
    CameraUiState.Capturing -> "camera_state_capturing"
    CameraUiState.PoseReady -> "camera_state_pose_ready"
    CameraUiState.Error -> "camera_state_error"
}

@Composable
private fun CameraStatusPill() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 96.dp),
        contentAlignment = Alignment.Center,
    ) {
        CameraAiStatusPill()
    }
}

@Composable
private fun CameraCountdownOverlay() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "3",
                fontSize = 96.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.White,
                modifier = Modifier.testTag("countdown_number"),
            )
            Text(
                text = stringResource(R.string.camera_state_countdown),
                style = MaterialTheme.typography.labelLarge,
                color = AppColors.LightGray,
            )
        }
    }
}

@Composable
private fun CameraCapturingOverlay() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            shape = CircleShape,
            color = Color.Black.copy(alpha = 0.6f),
            contentColor = AppColors.White,
        ) {
            Text(
                text = stringResource(R.string.camera_state_capturing),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            )
        }
    }
}

@Composable
private fun CameraPoseReadyOverlay() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            shape = CircleShape,
            color = Color.Black.copy(alpha = 0.6f),
            contentColor = AppColors.White,
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_focus),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = AppColors.White,
                )
                Text(
                    text = stringResource(R.string.camera_state_pose_ready),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun CameraPermissionState(
    onAllow: () -> Unit,
    onNotNow: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = AppColors.DeepNavy)
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_camera),
            contentDescription = null,
            modifier = Modifier.size(56.dp),
            tint = AppColors.LightGray,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.camera_permission_title),
            style = MaterialTheme.typography.titleLarge,
            color = AppColors.White,
            modifier = Modifier.testTag("camera_permission_title"),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.camera_permission_body),
            style = MaterialTheme.typography.bodyMedium,
            color = AppColors.LightGray,
            modifier = Modifier.testTag("camera_permission_body"),
        )
        Spacer(modifier = Modifier.height(28.dp))
        PermissionActions(
            onAllow = onAllow,
            onNotNow = onNotNow,
        )
    }
}

@Composable
private fun PermissionActions(
    onAllow: () -> Unit,
    onNotNow: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        PrimaryButton(
            text = stringResource(R.string.button_allow_camera),
            onClick = onAllow,
            modifier = Modifier.testTag("button_allow_camera"),
        )
        SecondaryButton(
            text = stringResource(R.string.button_not_now),
            onClick = onNotNow,
            modifier = Modifier.testTag("button_not_now"),
        )
    }
}

@Preview(name = "Camera", showBackground = true)
@Composable
private fun CameraScreenPreview() {
    PosePilotTheme {
        CameraPermissionState(onAllow = {}, onNotNow = {})
    }
}
