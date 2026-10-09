package com.posepilot.app.shared.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.posepilot.app.core.theme.AppColors

@Composable
fun ErrorStateView(
    title: String,
    body: String,
    primaryText: String,
    onPrimary: () -> Unit,
    modifier: Modifier = Modifier,
    secondaryText: String? = null,
    onSecondary: (() -> Unit)? = null,
    testTag: String = "error_state",
    primaryTestTag: String = "error_primary",
    secondaryTestTag: String = "error_secondary",
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .testTag(testTag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            tint = AppColors.Gray,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = AppColors.White,
            textAlign = TextAlign.Center,
        )
        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium,
            color = AppColors.LightGray,
            textAlign = TextAlign.Center,
        )
        PrimaryButton(
            text = primaryText,
            onClick = onPrimary,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .testTag(primaryTestTag),
        )
        if (secondaryText != null && onSecondary != null) {
            SecondaryButton(
                text = secondaryText,
                onClick = onSecondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(secondaryTestTag),
            )
        }
    }
}
