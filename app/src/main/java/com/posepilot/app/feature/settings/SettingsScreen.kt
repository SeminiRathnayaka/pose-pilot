package com.posepilot.app.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppCardStyles
import com.posepilot.app.core.theme.AppColors
import com.posepilot.app.core.theme.AppSpacing
import com.posepilot.app.core.theme.PosePilotTheme
import com.posepilot.app.shared.widgets.PosePilotTopBar
import com.posepilot.app.shared.widgets.SettingsGroup
import com.posepilot.app.shared.widgets.SettingsRow
import com.posepilot.app.shared.widgets.SettingsSectionHeader

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsContent(onBack = onBack, modifier = modifier)
}

@Composable
private fun SettingsContent(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_settings")
            .verticalScroll(rememberScrollState()),
    ) {
        PosePilotTopBar(
            title = stringResource(R.string.settings_title),
            showBack = true,
            onBack = onBack,
        )

        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = AppSpacing.sectionVertical,
            ),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            SettingsSectionHeader(title = stringResource(R.string.settings_section_camera))
            SettingsGroup {
                var countdownIndex by rememberSaveable { mutableIntStateOf(0) }
                val countdownLabels = listOf(
                    stringResource(R.string.setting_value_countdown_3s),
                    stringResource(R.string.setting_value_countdown_5s),
                    stringResource(R.string.setting_value_countdown_10s),
                )
                SettingsRow(
                    title = stringResource(R.string.setting_countdown),
                    value = countdownLabels[countdownIndex],
                    onClick = { countdownIndex = (countdownIndex + 1) % countdownLabels.size },
                    testTag = "setting_countdown",
                )

                var gridEnabled by rememberSaveable { mutableStateOf(true) }
                SettingsRow(
                    title = stringResource(R.string.setting_grid_overlay),
                    checked = gridEnabled,
                    onCheckedChange = { gridEnabled = it },
                    testTag = "setting_grid_overlay",
                )

                var mirrorEnabled by rememberSaveable { mutableStateOf(true) }
                SettingsRow(
                    title = stringResource(R.string.setting_mirror),
                    checked = mirrorEnabled,
                    onCheckedChange = { mirrorEnabled = it },
                    testTag = "setting_mirror",
                )

                var autoCaptureEnabled by rememberSaveable { mutableStateOf(false) }
                SettingsRow(
                    title = stringResource(R.string.setting_auto_capture),
                    checked = autoCaptureEnabled,
                    onCheckedChange = { autoCaptureEnabled = it },
                    testTag = "setting_auto_capture",
                )

                var backCamera by rememberSaveable { mutableStateOf(true) }
                SettingsRow(
                    title = stringResource(R.string.setting_default_camera),
                    value = stringResource(
                        if (backCamera) R.string.value_camera_back else R.string.value_camera_front,
                    ),
                    onClick = { backCamera = !backCamera },
                    testTag = "setting_default_camera",
                )
            }

            SettingsSectionHeader(title = stringResource(R.string.settings_section_guidance))
            SettingsGroup {
                var intensityIndex by rememberSaveable { mutableIntStateOf(1) }
                val intensityLabels = listOf(
                    stringResource(R.string.value_intensity_low),
                    stringResource(R.string.value_intensity_medium),
                    stringResource(R.string.value_intensity_high),
                )
                SettingsRow(
                    title = stringResource(R.string.setting_guidance_intensity),
                    value = intensityLabels[intensityIndex],
                    onClick = { intensityIndex = (intensityIndex + 1) % intensityLabels.size },
                    testTag = "setting_guidance_intensity",
                )

                var hapticEnabled by rememberSaveable { mutableStateOf(true) }
                SettingsRow(
                    title = stringResource(R.string.setting_haptic),
                    checked = hapticEnabled,
                    onCheckedChange = { hapticEnabled = it },
                    testTag = "setting_haptic",
                )

                var soundEnabled by rememberSaveable { mutableStateOf(true) }
                SettingsRow(
                    title = stringResource(R.string.setting_sound),
                    checked = soundEnabled,
                    onCheckedChange = { soundEnabled = it },
                    testTag = "setting_sound",
                )

                var referenceOverlay by rememberSaveable { mutableStateOf(true) }
                SettingsRow(
                    title = stringResource(R.string.setting_reference_overlay),
                    checked = referenceOverlay,
                    onCheckedChange = { referenceOverlay = it },
                    testTag = "setting_reference_overlay",
                )
            }

            SettingsSectionHeader(title = stringResource(R.string.settings_section_appearance))
            SettingsGroup {
                SettingsRow(
                    title = stringResource(R.string.setting_theme),
                    value = stringResource(R.string.setting_value_navy),
                    testTag = "setting_theme",
                )
                SettingsRow(
                    title = stringResource(R.string.setting_app_appearance),
                    value = stringResource(R.string.setting_value_dark),
                    testTag = "setting_app_appearance",
                )
            }

            SettingsSectionHeader(title = stringResource(R.string.settings_section_privacy))
            SettingsGroup {
                PrivacyInfoRow(
                    title = stringResource(R.string.privacy_camera_title),
                    body = stringResource(R.string.privacy_camera_body),
                )
                PrivacyInfoRow(
                    title = stringResource(R.string.privacy_processing_title),
                    body = stringResource(R.string.privacy_processing_body),
                )
            }

            SettingsSectionHeader(title = stringResource(R.string.settings_section_about))
            SettingsGroup {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.about_description),
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppColors.LightGray,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.about_acknowledgements),
                        style = MaterialTheme.typography.bodySmall,
                        color = AppColors.Gray,
                    )
                }
                SettingsRow(
                    title = stringResource(R.string.about_version),
                    value = rememberAppVersion(),
                    testTag = "setting_version",
                )
            }
        }
    }
}

@Composable
private fun PrivacyInfoRow(
    title: String,
    body: String,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = AppColors.White,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = body,
            style = MaterialTheme.typography.bodySmall,
            color = AppColors.Gray,
        )
    }
}

@Composable
private fun rememberAppVersion(): String {
    val context = LocalContext.current
    return remember(context) {
        runCatching {
            val info = context.packageManager.getPackageInfo(context.packageName, 0)
            info.versionName ?: "—"
        }.getOrDefault("—")
    }
}

@Preview(name = "Settings", showBackground = true)
@Composable
private fun SettingsPreview() {
    PosePilotTheme {
        SettingsContent(onBack = {})
    }
}
