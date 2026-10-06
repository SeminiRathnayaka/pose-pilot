package com.posepilot.app.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = AppColors.Accent,
    onPrimary = AppColors.OnAccent,
    primaryContainer = AppColors.Accent,
    onPrimaryContainer = AppColors.OnAccent,
    secondary = AppColors.LightOnSurface,
    onSecondary = AppColors.LightSurface,
    secondaryContainer = AppColors.LightSurfaceVariant,
    onSecondaryContainer = AppColors.LightOnSurface,
    background = AppColors.LightBackground,
    onBackground = AppColors.LightOnSurface,
    surface = AppColors.LightSurface,
    onSurface = AppColors.LightOnSurface,
    surfaceVariant = AppColors.LightSurfaceVariant,
    onSurfaceVariant = AppColors.LightOnSurfaceVariant,
    outline = AppColors.LightOutline,
    outlineVariant = AppColors.LightOutlineVariant,
    error = AppColors.Error,
    onError = AppColors.LightOnError,
)

private val DarkColors = darkColorScheme(
    primary = AppColors.AccentDark,
    onPrimary = AppColors.OnAccentDark,
    primaryContainer = AppColors.AccentDark,
    onPrimaryContainer = AppColors.OnAccentDark,
    secondary = AppColors.DarkOnSurface,
    onSecondary = AppColors.DarkBackground,
    secondaryContainer = AppColors.DarkSurfaceVariant,
    onSecondaryContainer = AppColors.DarkOnSurface,
    background = AppColors.DarkBackground,
    onBackground = AppColors.DarkOnSurface,
    surface = AppColors.DarkSurface,
    onSurface = AppColors.DarkOnSurface,
    surfaceVariant = AppColors.DarkSurfaceVariant,
    onSurfaceVariant = AppColors.DarkOnSurfaceVariant,
    outline = AppColors.DarkOutline,
    outlineVariant = AppColors.DarkOutlineVariant,
    error = AppColors.DarkError,
    onError = AppColors.DarkOnError,
)

private val AppShapes = androidx.compose.material3.Shapes(
    extraSmall = RoundedCornerShape(AppRadius.xs),
    small = RoundedCornerShape(AppRadius.sm),
    medium = RoundedCornerShape(AppRadius.md),
    large = RoundedCornerShape(AppRadius.lg),
    extraLarge = RoundedCornerShape(AppRadius.xl),
)

@Composable
fun PosePilotTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}

@Composable
fun appCornerShape(): Shape = MaterialTheme.shapes.medium
