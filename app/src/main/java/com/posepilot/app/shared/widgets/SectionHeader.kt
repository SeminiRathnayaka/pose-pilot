package com.posepilot.app.shared.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.posepilot.app.core.theme.AppColors

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = AppColors.White,
        )
        if (actionText != null && onAction != null) {
            Text(
                text = actionText,
                style = MaterialTheme.typography.labelLarge,
                color = AppColors.LightGray,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable(onClick = onAction)
                    .testTag("section_action"),
            )
        }
    }
}

@Composable
fun EmptyStateView(
    title: String,
    modifier: Modifier = Modifier,
    body: String? = null,
    icon: ImageVector? = null,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
    testTag: String = "empty_state",
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .testTag(testTag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = AppColors.Gray,
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = AppColors.LightGray,
            textAlign = TextAlign.Center,
        )
        if (body != null) {
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.Gray,
                textAlign = TextAlign.Center,
            )
        }
        if (actionText != null && onAction != null) {
            SecondaryButton(
                text = actionText,
                onClick = onAction,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}
