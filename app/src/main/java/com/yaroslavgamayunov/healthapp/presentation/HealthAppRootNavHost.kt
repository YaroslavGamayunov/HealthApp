package com.yaroslavgamayunov.healthapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.health.connect.client.HealthConnectClient.Companion.SDK_AVAILABLE
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yaroslavgamayunov.healthapp.data.HealthConnectManager
import com.yaroslavgamayunov.healthapp.presentation.auth.LoginScreen
import com.yaroslavgamayunov.healthapp.presentation.questions.QuestionsScreen
import com.yaroslavgamayunov.healthapp.presentation.welcome.WelcomeScreen

@Composable
fun HealthRootNavHost(
    navController: NavHostController,
    healthConnectManager: HealthConnectManager,
    modifier: Modifier = Modifier,
) {
    val healthConnectAvailability by healthConnectManager.availability

    val startDestination = when {
        healthConnectAvailability == SDK_AVAILABLE -> Screen.LoginScreen.route // todo check auth state
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
            HealthAppContent(
                rootNavController = navController,
                healthConnectManager = healthConnectManager
            )
        }
        composable(route = Questions.route) {
            QuestionsScreen(rootNavController = navController, viewModel())
        }

        composable(Screen.LoginScreen.route) {
            LoginScreen(rootNavController = navController)
        }
    }
}