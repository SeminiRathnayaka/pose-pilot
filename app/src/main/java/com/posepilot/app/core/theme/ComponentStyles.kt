package com.posepilot.app.core.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

object AppButtonStyles {
    val shape: Shape = RoundedCornerShape(AppRadius.md)
    val minHeight = 52.dp

    @Composable
    fun primary(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    @Composable
    fun secondary(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurface,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    @Composable
    fun secondaryBorder(): BorderStroke = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline,
    )
}

object AppCardStyles {
    val shape: Shape = RoundedCornerShape(AppRadius.lg)

    @Composable
    fun default(): CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    )

    @Composable
    fun muted(): CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

object AppInputStyles {
    @Composable
    fun default(): TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
        cursorColor = MaterialTheme.colorScheme.primary,
    )
}

object AppTopBarStyles {
    @Composable
    fun containerColor(): Color = MaterialTheme.colorScheme.surface

    @Composable
    fun contentColor(): Color = MaterialTheme.colorScheme.onSurface
}

object AppNavigationStyles {
    @Composable
    fun containerColor(): Color = MaterialTheme.colorScheme.surface

    @Composable
    fun selectedIconColor(): Color = MaterialTheme.colorScheme.primary

    @Composable
    fun unselectedIconColor(): Color = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun indicatorColor(): Color = MaterialTheme.colorScheme.secondaryContainer
}
