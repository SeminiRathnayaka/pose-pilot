package com.posepilot.app.core.navigation

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
import com.posepilot.app.feature.history.HistoryScreen
import com.posepilot.app.feature.home.HomeScreen
import com.posepilot.app.feature.poses.PoseLibraryScreen
import com.posepilot.app.feature.profile.ProfileScreen
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
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    onStartSession = { mode ->
                        navController.navigate(Routes.camera(mode))
                    },
                    onBrowsePoses = {
                        navController.navigate(Routes.POSES)
                    },
                )
            }
            composable(
                route = Routes.CAMERA_WITH_MODE,
                arguments = listOf(
                    navArgument("poseMode") {
                        type = NavType.StringType
                        defaultValue = PoseMode.SOLO.key
                    },
                ),
            ) { entry ->
                val modeKey = entry.arguments?.getString("poseMode")
                CameraScreen(poseMode = PoseMode.fromKey(modeKey))
            }
            composable(Routes.POSES) {
                PoseLibraryScreen()
            }
            composable(Routes.HISTORY) {
                HistoryScreen()
            }
            composable(Routes.PROFILE) {
                ProfileScreen()
            }
        }
    }
}
