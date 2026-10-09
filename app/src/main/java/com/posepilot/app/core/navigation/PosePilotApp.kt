package com.posepilot.app.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.posepilot.app.feature.camera.CameraScreen
import com.posepilot.app.feature.explore.ExploreScreen
import com.posepilot.app.feature.history.HistoryScreen
import com.posepilot.app.feature.home.HomeScreen
import com.posepilot.app.feature.pose.PoseDetailsScreen
import com.posepilot.app.feature.profile.ProfileScreen
import com.posepilot.app.feature.roulette.PoseRouletteScreen
import com.posepilot.app.feature.settings.SettingsScreen
import com.posepilot.app.shared.model.PoseMode

@Composable
fun PosePilotApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            AppBottomNavigation(
                currentDestination = currentDestination,
                onNavigate = { route ->
                    val target = if (route == Routes.CAMERA) {
                        Routes.camera(PoseMode.SOLO)
                    } else {
                        route
                    }
                    navController.navigate(target) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(animationSpec = tween(220)) },
            exitTransition = { fadeOut(animationSpec = tween(180)) },
            popEnterTransition = { fadeIn(animationSpec = tween(220)) },
            popExitTransition = { fadeOut(animationSpec = tween(180)) },
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    onStartShooting = { mode ->
                        navController.navigate(Routes.camera(mode))
                    },
                    onExplorePoses = {
                        navController.navigate(Routes.EXPLORE)
                    },
                    onOpenRoulette = {
                        navController.navigate(Routes.ROULETTE)
                    },
                    onOpenPose = { poseId ->
                        navController.navigate(Routes.pose(poseId))
                    },
                )
            }
            composable(Routes.EXPLORE) {
                ExploreScreen(
                    onOpenPose = { poseId ->
                        navController.navigate(Routes.pose(poseId))
                    },
                )
            }
            composable(
                route = Routes.POSE,
                arguments = listOf(
                    navArgument("poseId") { type = NavType.StringType },
                ),
            ) { entry ->
                val poseId = entry.arguments?.getString("poseId").orEmpty()
                PoseDetailsScreen(
                    poseId = poseId,
                    onBack = { navController.popBackStack() },
                    onTryPose = { pose, mode ->
                        navController.navigate(
                            Routes.camera(
                                poseMode = mode,
                                refPoseId = pose.id,
                            ),
                        )
                    },
                )
            }
            composable(
                route = Routes.CAMERA,
                arguments = listOf(
                    navArgument("poseMode") {
                        type = NavType.StringType
                        defaultValue = PoseMode.SOLO.key
                    },
                    navArgument("refPoseId") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                ),
            ) { entry ->
                val modeKey = entry.arguments?.getString("poseMode")
                val refPoseId = entry.arguments?.getString("refPoseId").orEmpty()
                CameraScreen(
                    poseMode = PoseMode.fromKey(modeKey),
                    refPoseId = refPoseId,
                    onBack = { navController.popBackStack() },
                    onOpenSettings = { navController.navigate(Routes.SETTINGS) },
                )
            }
            composable(Routes.ROULETTE) {
                PoseRouletteScreen(
                    onBack = { navController.popBackStack() },
                    onOpenPose = { poseId -> navController.navigate(Routes.pose(poseId)) },
                    onTryPose = { pose ->
                        navController.navigate(
                            Routes.camera(
                                poseMode = pose.mode,
                                refPoseId = pose.id,
                            ),
                        )
                    },
                )
            }
            composable(Routes.HISTORY) {
                HistoryScreen(
                    onOpenExplore = { navController.navigate(Routes.EXPLORE) },
                    onOpenPose = { poseId -> navController.navigate(Routes.pose(poseId)) },
                )
            }
            composable(Routes.PROFILE) {
                ProfileScreen(
                    onOpenSettings = { navController.navigate(Routes.SETTINGS) },
                )
            }
            composable(Routes.SETTINGS) {
                SettingsScreen(
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}
