package com.yaroslavgamayunov.healthapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.health.connect.client.HealthConnectClient.Companion.SDK_AVAILABLE
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yaroslavgamayunov.healthapp.data.HealthConnectManager
import com.yaroslavgamayunov.healthapp.presentation.welcome.WelcomeScreen

@Composable
fun HealthRootNavHost(
    navController: NavHostController,
    healthConnectManager: HealthConnectManager,
    modifier: Modifier = Modifier,
) {
    val healthConnectAvailability by healthConnectManager.availability

    val startDestination = when {
        healthConnectAvailability == SDK_AVAILABLE -> Screen.ContentScreen.route
        else -> Screen.WelcomeScreen.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.WelcomeScreen.route) {
            val availability by healthConnectManager.availability
            WelcomeScreen(
                healthConnectAvailability = availability,
                onResumeAvailabilityCheck = {
                    healthConnectManager.checkAvailability()
                }
            )
        }
        composable(Screen.ContentScreen.route) {
            HealthAppContent(healthConnectManager = healthConnectManager)
        }
    }
}