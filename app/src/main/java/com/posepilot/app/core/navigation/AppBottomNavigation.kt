package com.posepilot.app.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.posepilot.app.core.theme.AppColors
import com.posepilot.app.core.theme.AppNavigationStyles

@Composable
fun AppBottomNavigation(
    currentDestination: NavDestination?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = AppNavigationStyles.containerColor(),
    ) {
        bottomNavItems.forEach { item ->
            val label = stringResource(item.labelRes)
            val selected = currentDestination.isRouteSelected(item.route)
            val iconColor = if (selected) {
                AppNavigationStyles.selectedIconColor()
            } else {
                AppNavigationStyles.unselectedIconColor()
            }
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = {
                    if (item.isPrimary) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = if (selected) AppColors.White else AppColors.DarkGray,
                                    shape = CircleShape,
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                painter = item.icon.toPainter(),
                                contentDescription = null,
                                tint = if (selected) AppColors.DeepNavy else AppColors.Gray,
                            )
                        }
                    } else {
                        Icon(
                            painter = item.icon.toPainter(),
                            contentDescription = null,
                            tint = iconColor,
                        )
                    }
                },
                label = { Text(text = label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppNavigationStyles.selectedIconColor(),
                    unselectedIconColor = AppNavigationStyles.unselectedIconColor(),
                    selectedTextColor = AppNavigationStyles.selectedTextColor(),
                    unselectedTextColor = AppNavigationStyles.unselectedTextColor(),
                    indicatorColor = AppNavigationStyles.indicatorColor(),
                ),
                modifier = Modifier.testTag("nav_${item.tag}"),
            )
        }
    }
}

private fun NavDestination?.isRouteSelected(route: String): Boolean {
    if (this == null) return false
    return hierarchy.any { destination ->
        destination.route == route ||
            (route == Routes.CAMERA && destination.route?.startsWith("camera") == true) ||
            (route == Routes.EXPLORE && destination.route?.startsWith("pose/") == true)
    }
}

@Composable
private fun BottomNavItem.BottomNavIcon.toPainter(): Painter = when (this) {
    is BottomNavItem.BottomNavIcon.Vector -> rememberVectorPainter(imageVector)
    is BottomNavItem.BottomNavIcon.Res -> painterResource(id = resId)
}
