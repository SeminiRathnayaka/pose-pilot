package com.posepilot.app.core.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.posepilot.app.R
import com.posepilot.app.shared.model.PoseMode

object Routes {
    const val HOME = "home"
    const val CAMERA = "camera"
    const val CAMERA_WITH_MODE = "camera/{poseMode}"
    const val POSES = "poses"
    const val HISTORY = "history"
    const val PROFILE = "profile"

    fun camera(poseMode: PoseMode): String = "$CAMERA/${poseMode.key}"
}

data class BottomNavItem(
    val route: String,
    @StringRes val labelRes: Int,
    val icon: BottomNavIcon,
) {
    sealed interface BottomNavIcon {
        data class Vector(val imageVector: ImageVector) : BottomNavIcon
        data class Res(val resId: Int) : BottomNavIcon
    }
}

val bottomNavItems: List<BottomNavItem> = listOf(
    BottomNavItem(
        route = Routes.HOME,
        labelRes = R.string.nav_home,
        icon = BottomNavItem.BottomNavIcon.Vector(Icons.Filled.Home),
    ),
    BottomNavItem(
        route = Routes.CAMERA,
        labelRes = R.string.nav_camera,
        icon = BottomNavItem.BottomNavIcon.Res(R.drawable.ic_camera),
    ),
    BottomNavItem(
        route = Routes.POSES,
        labelRes = R.string.nav_poses,
        icon = BottomNavItem.BottomNavIcon.Res(R.drawable.ic_poses),
    ),
    BottomNavItem(
        route = Routes.HISTORY,
        labelRes = R.string.nav_history,
        icon = BottomNavItem.BottomNavIcon.Res(R.drawable.ic_history),
    ),
    BottomNavItem(
        route = Routes.PROFILE,
        labelRes = R.string.nav_profile,
        icon = BottomNavItem.BottomNavIcon.Vector(Icons.Filled.Person),
    ),
)
