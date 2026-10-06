package com.posepilot.app.feature.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.posepilot.app.R
import com.posepilot.app.core.theme.PosePilotTheme
import com.posepilot.app.shared.widgets.PlaceholderContent
import com.posepilot.app.shared.widgets.ScreenHeader

@Composable
fun HistoryScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
    ) {
        ScreenHeader(
            title = stringResource(R.string.nav_history),
            subtitle = stringResource(R.string.placeholder_history_body),
        )
        PlaceholderContent(
            title = stringResource(R.string.placeholder_history_title),
            body = stringResource(R.string.history_empty),
            testTag = "screen_history",
        )
    }
}

@Preview(name = "History light", showBackground = true)
@Composable
private fun HistoryScreenPreview() {
    PosePilotTheme {
        HistoryScreen()
    }
}
