package com.posepilot.app.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppInputStyles
import com.posepilot.app.core.theme.PosePilotTheme
import com.posepilot.app.shared.widgets.PlaceholderContent
import com.posepilot.app.shared.widgets.ScreenHeader

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_profile")
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        ScreenHeader(
            title = stringResource(R.string.nav_profile),
            subtitle = stringResource(R.string.placeholder_profile_body),
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = stringResource(R.string.profile_name_hint)) },
            singleLine = true,
            colors = AppInputStyles.default(),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_profile_name"),
        )
        PlaceholderContent(
            title = stringResource(R.string.placeholder_profile_title),
            body = stringResource(R.string.placeholder_profile_body),
            testTag = "screen_profile_placeholder",
        )
    }
}

@Preview(name = "Profile light", showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    PosePilotTheme {
        ProfileScreen()
    }
}
