package com.posepilot.app.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape

private val PosePilotColors = darkColorScheme(
    primary = AppColors.White,
    onPrimary = AppColors.DeepNavy,
    primaryContainer = AppColors.DarkGray,
    onPrimaryContainer = AppColors.White,
    secondary = AppColors.LightGray,
    onSecondary = AppColors.DeepNavy,
    secondaryContainer = AppColors.DarkGray,
    onSecondaryContainer = AppColors.White,
    tertiary = AppColors.Gray,
    onTertiary = AppColors.DeepNavy,
    tertiaryContainer = AppColors.Navy,
    onTertiaryContainer = AppColors.LightGray,
    background = AppColors.DeepNavy,
    onBackground = AppColors.White,
    surface = AppColors.Navy,
    onSurface = AppColors.White,
    surfaceVariant = AppColors.DarkGray,
    onSurfaceVariant = AppColors.LightGray,
    surfaceTint = AppColors.White,
    outline = AppColors.LightGray,
    outlineVariant = AppColors.FaintBorder,
    error = AppColors.LightGray,
    onError = AppColors.DeepNavy,
    inverseSurface = AppColors.LightGray,
    inverseOnSurface = AppColors.DeepNavy,
    scrim = AppColors.Scrim,
)

private val AppShapes = androidx.compose.material3.Shapes(
    extraSmall = RoundedCornerShape(AppRadius.xs),
    small = RoundedCornerShape(AppRadius.sm),
    medium = RoundedCornerShape(AppRadius.md),
    large = RoundedCornerShape(AppRadius.lg),
    extraLarge = RoundedCornerShape(AppRadius.xl),
)

@Composable
fun PosePilotTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PosePilotColors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}

@Composable
fun appCornerShape(): Shape = MaterialTheme.shapes.medium
