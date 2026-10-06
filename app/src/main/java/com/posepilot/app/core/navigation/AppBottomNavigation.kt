package com.posepilot.app.core.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
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
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        painter = item.icon.toPainter(),
                        contentDescription = null,
                    )
                },
                label = { Text(text = label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppNavigationStyles.selectedIconColor(),
                    unselectedIconColor = AppNavigationStyles.unselectedIconColor(),
                    selectedTextColor = AppNavigationStyles.selectedIconColor(),
                    unselectedTextColor = AppNavigationStyles.unselectedIconColor(),
                    indicatorColor = AppNavigationStyles.indicatorColor(),
                ),
                modifier = Modifier.testTag("nav_${item.route}"),
            )
        }
    }
}

private fun NavDestination?.isRouteSelected(route: String): Boolean {
    if (this == null) return false
    return hierarchy.any { destination ->
        destination.route == route ||
            (route == Routes.CAMERA && destination.route?.startsWith(Routes.CAMERA) == true)
    }
}

@Composable
private fun BottomNavItem.BottomNavIcon.toPainter(): Painter = when (this) {
    is BottomNavItem.BottomNavIcon.Vector -> rememberVectorPainter(imageVector)
    is BottomNavItem.BottomNavIcon.Res -> painterResource(id = resId)
}
