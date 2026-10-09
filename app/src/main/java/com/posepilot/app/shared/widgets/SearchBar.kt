package com.posepilot.app.shared.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.posepilot.app.R
import com.posepilot.app.core.theme.AppColors
import com.posepilot.app.core.theme.AppRadius

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = stringResource(R.string.search_hint),
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .testTag("search_bar"),
        placeholder = {
            Text(
                text = hint,
                color = AppColors.Gray,
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(AppRadius.pill),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = AppColors.Gray,
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = { onQueryChange("") },
                    modifier = Modifier.testTag("search_clear"),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.search_clear),
                        tint = AppColors.Gray,
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = AppColors.DarkGray,
            unfocusedContainerColor = AppColors.DarkGray,
            focusedTextColor = AppColors.White,
            unfocusedTextColor = AppColors.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = AppColors.White,
            focusedPlaceholderColor = AppColors.Gray,
            unfocusedPlaceholderColor = AppColors.Gray,
        ),
        textStyle = MaterialTheme.typography.bodyLarge,
    )
}
