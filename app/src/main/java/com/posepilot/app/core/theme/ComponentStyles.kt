package com.posepilot.app.core.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

object AppButtonStyles {
    val shape: Shape = RoundedCornerShape(AppRadius.md)
    val minHeight = AppSpacing.minTouchTarget + 4.dp

    @Composable
    fun primary(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = AppColors.DarkGray,
        disabledContentColor = AppColors.Gray,
    )

    @Composable
    fun secondary(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = AppColors.Gray,
    )

    @Composable
    fun secondaryBorder(): BorderStroke = BorderStroke(
        width = 1.dp,
        color = AppColors.SubtleBorder,
    )
}

object AppCardStyles {
    val shape: Shape = RoundedCornerShape(AppRadius.md)
    val heroShape: Shape = RoundedCornerShape(AppRadius.xl)

    @Composable
    fun default(): CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    )

    @Composable
    fun muted(): CardColors = CardDefaults.cardColors(
        containerColor = AppColors.DarkGray,
        contentColor = MaterialTheme.colorScheme.onSurface,
    )

    @Composable
    fun immersive(): CardColors = CardDefaults.cardColors(
        containerColor = AppColors.Black,
        contentColor = MaterialTheme.colorScheme.onSurface,
    )
}

object AppInputStyles {
    @Composable
    fun default() = TextFieldDefaults.colors(
        focusedContainerColor = AppColors.DarkGray,
        unfocusedContainerColor = AppColors.DarkGray,
        focusedTextColor = AppColors.White,
        unfocusedTextColor = AppColors.White,
        focusedLabelColor = AppColors.LightGray,
        unfocusedLabelColor = AppColors.Gray,
        focusedPlaceholderColor = AppColors.Gray,
        unfocusedPlaceholderColor = AppColors.Gray,
        focusedIndicatorColor = AppColors.White,
        unfocusedIndicatorColor = AppColors.SubtleBorder,
        focusedLeadingIconColor = AppColors.LightGray,
        unfocusedLeadingIconColor = AppColors.Gray,
        cursorColor = AppColors.White,
        selectionColors = TextSelectionColors(
            handleColor = AppColors.White,
            backgroundColor = AppColors.DarkGray,
        ),
    )
}

object AppTopBarStyles {
    @Composable
    fun containerColor(): Color = Color.Transparent

    @Composable
    fun contentColor(): Color = MaterialTheme.colorScheme.onBackground
}

object AppNavigationStyles {
    @Composable
    fun containerColor(): Color = AppColors.Navy

    @Composable
    fun selectedIconColor(): Color = AppColors.White

    @Composable
    fun unselectedIconColor(): Color = AppColors.Gray

    @Composable
    fun selectedTextColor(): Color = AppColors.White

    @Composable
    fun unselectedTextColor(): Color = AppColors.Gray

    @Composable
    fun indicatorColor(): Color = AppColors.DarkGray
}
