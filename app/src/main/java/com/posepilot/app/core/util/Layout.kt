package com.posepilot.app.core.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun gridColumnsFor(maxWidth: Dp): Int = when {
    maxWidth < 360.dp -> 2
    maxWidth < 600.dp -> 3
    else -> 4
}
