package com.posepilot.app.shared.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.posepilot.app.core.theme.AppColors
import com.posepilot.app.core.theme.AppRadius

@Composable
fun CategoryChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "category_chip",
) {
    Surface(
        onClick = onClick,
        modifier = modifier.testTag(testTag),
        shape = RoundedCornerShape(AppRadius.pill),
        color = if (selected) AppColors.White else AppColors.DarkGray,
        contentColor = if (selected) AppColors.DeepNavy else AppColors.LightGray,
        border = if (selected) null else BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
        )
    }
}
