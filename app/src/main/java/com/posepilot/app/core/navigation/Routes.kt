package com.posepilot.app.core.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.posepilot.app.R
import com.posepilot.app.shared.model.PoseMode

object Routes {
    const val HOME = "home"
    const val EXPLORE = "explore"
    const val POSE = "pose/{poseId}"
    const val CAMERA = "camera/{poseMode}?refPoseId={refPoseId}"
    const val ROULETTE = "roulette"
    const val HISTORY = "history"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"

    fun pose(poseId: String): String = "pose/$poseId"

    fun camera(
        poseMode: PoseMode = PoseMode.SOLO,
        refPoseId: String = "",
    ): String = "camera/${poseMode.key}?refPoseId=$refPoseId"
}

data class BottomNavItem(
    val route: String,
    @param:StringRes val labelRes: Int,
    val icon: BottomNavIcon,
    val isPrimary: Boolean = false,
    val tag: String = route.substringBefore('/').substringBefore('?'),
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
        route = Routes.EXPLORE,
        labelRes = R.string.nav_explore,
        icon = BottomNavItem.BottomNavIcon.Vector(Icons.Filled.Search),
    ),
    BottomNavItem(
        route = Routes.CAMERA,
        labelRes = R.string.nav_camera,
        icon = BottomNavItem.BottomNavIcon.Res(R.drawable.ic_camera),
        isPrimary = true,
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
