package com.posepilot.app.feature.camera

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppColors

@Composable
fun CameraControlButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    icon: Painter? = null,
    imageVector: ImageVector? = null,
    contentDescription: String = label,
) {
    Column(
        modifier = modifier.testTag("camera_control_${label.lowercase().replace(' ', '_')}"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Surface(
            onClick = onClick,
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            color = if (selected) AppColors.White else AppColors.DarkGray,
            contentColor = if (selected) AppColors.DeepNavy else AppColors.White,
            border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
        ) {
            Box(contentAlignment = Alignment.Center) {
                when {
                    icon != null -> Icon(
                        painter = icon,
                        contentDescription = contentDescription,
                        modifier = Modifier.size(22.dp),
                    )
                    imageVector != null -> Icon(
                        imageVector = imageVector,
                        contentDescription = contentDescription,
                        modifier = Modifier.size(22.dp),
                    )
                }
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) AppColors.White else AppColors.LightGray,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun CameraShutterButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    capturing: Boolean = false,
) {
    val shutterDescription = stringResource(R.string.camera_shutter)
    Surface(
        onClick = onClick,
        modifier = modifier
            .size(76.dp)
            .testTag("camera_shutter")
            .semantics { contentDescription = shutterDescription },
        shape = CircleShape,
        color = Color.Transparent,
        border = BorderStroke(width = 4.dp, color = AppColors.White),
        contentColor = AppColors.White,
    ) {
        Box(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize()
                .background(
                    color = if (capturing) AppColors.LightGray else AppColors.White,
                    shape = CircleShape,
                ),
        )
    }
}

@Composable
fun CameraPreviewPlaceholder(
    modifier: Modifier = Modifier,
    showGrid: Boolean = true,
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppColors.Black)
            .testTag("camera_preview"),
        contentAlignment = Alignment.Center,
    ) {
        if (showGrid) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val stroke = 1.dp.toPx()
                val lineColor = Color.White.copy(alpha = 0.14f)
                drawLine(lineColor, Offset(size.width / 3f, 0f), Offset(size.width / 3f, size.height), strokeWidth = stroke)
                drawLine(lineColor, Offset(size.width * 2f / 3f, 0f), Offset(size.width * 2f / 3f, size.height), strokeWidth = stroke)
                drawLine(lineColor, Offset(0f, size.height / 3f), Offset(size.width, size.height / 3f), strokeWidth = stroke)
                drawLine(lineColor, Offset(0f, size.height * 2f / 3f), Offset(size.width, size.height * 2f / 3f), strokeWidth = stroke)
            }
        }
        FramingMarkers(modifier = Modifier.fillMaxSize())
        content()
        Text(
            text = stringResource(R.string.camera_preview_placeholder),
            style = MaterialTheme.typography.labelSmall,
            color = AppColors.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
        )
    }
}

@Composable
private fun FramingMarkers(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val length = 28.dp.toPx()
        val stroke = 2.dp.toPx()
        val inset = 24.dp.toPx()
        val markerColor = Color.White.copy(alpha = 0.65f)
        val w = size.width
        val h = size.height
        fun corner(x1: Float, y1: Float, dx: Float, dy: Float) {
            drawLine(markerColor, Offset(x1, y1), Offset(x1 + dx, y1), strokeWidth = stroke)
            drawLine(markerColor, Offset(x1, y1), Offset(x1, y1 + dy), strokeWidth = stroke)
        }
        corner(inset, inset, length, length)
        corner(w - inset, inset, -length, length)
        corner(inset, h - inset, length, -length)
        corner(w - inset, h - inset, -length, -length)
    }
}

@Composable
fun CameraAiStatusPill(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.camera_status_preview),
    subtitle: String = stringResource(R.string.camera_status_guidance),
) {
    Surface(
        modifier = modifier.testTag("camera_status_pill"),
        shape = CircleShape,
        color = Color.Black.copy(alpha = 0.55f),
        contentColor = AppColors.White,
        border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color = AppColors.White, shape = CircleShape),
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = AppColors.White,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = AppColors.LightGray,
                )
            }
        }
    }
}

@Composable
fun CameraReferenceThumbnail(
    poseName: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.testTag("camera_reference"),
        shape = AppShapesThumb,
        color = AppColors.Black,
        contentColor = AppColors.White,
        border = BorderStroke(width = 1.dp, color = AppColors.SubtleBorder),
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.camera_reference),
                style = MaterialTheme.typography.labelSmall,
                color = AppColors.Gray,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = poseName,
                style = MaterialTheme.typography.labelMedium,
                color = AppColors.White,
                textAlign = TextAlign.Center,
            )
        }
    }
}

private val AppShapesThumb = RoundedCornerShape(12.dp)
