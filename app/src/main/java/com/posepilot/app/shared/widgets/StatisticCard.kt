package com.posepilot.app.shared.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.posepilot.app.core.theme.AppColors
import com.posepilot.app.core.theme.AppCardStyles

@Composable
fun StatisticCard(
    value: String,
    label: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    testTag: String = "statistic_card",
) {
    Surface(
        modifier = modifier.testTag(testTag),
        shape = AppCardStyles.shape,
        color = AppColors.Navy,
        contentColor = AppColors.White,
        border = BorderStroke(width = 1.dp, color = AppColors.FaintBorder),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = AppColors.Gray,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = AppColors.White,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = AppColors.LightGray,
            )
        }
    }
}
