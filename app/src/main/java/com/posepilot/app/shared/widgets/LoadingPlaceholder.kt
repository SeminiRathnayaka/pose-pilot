package com.posepilot.app.shared.widgets

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.posepilot.app.core.theme.AppCardStyles
import com.posepilot.app.core.theme.AppColors

@Composable
fun LoadingPlaceholder(
    modifier: Modifier = Modifier,
    shape: Shape = AppCardStyles.shape,
    testTag: String = "loading_placeholder",
) {
    PulseBox(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 220.dp)
            .background(color = AppColors.DarkGray, shape = shape)
            .testTag(testTag),
    )
}

@Composable
fun LoadingLine(
    modifier: Modifier = Modifier,
    testTag: String = "loading_line",
) {
    PulseBox(
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(color = AppColors.DarkGray, shape = RectangleShape)
            .testTag(testTag),
    )
}

@Composable
private fun PulseBox(modifier: Modifier) {
    val transition = rememberInfiniteTransition(label = "placeholder")
    val alpha by transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "placeholder_alpha",
    )
    Box(
        modifier = modifier.graphicsLayer { this.alpha = alpha },
    )
}
