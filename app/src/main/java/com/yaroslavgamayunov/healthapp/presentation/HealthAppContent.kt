package com.yaroslavgamayunov.healthapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.yaroslavgamayunov.healthapp.data.HealthConnectManager

@Composable
fun HealthAppContent(
    healthConnectManager: HealthConnectManager
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            if (navController.currentDestination?.route != Screen.WelcomeScreen.route) {
                HealthAppTabRow(
                    allScreens = healthAppTabRowScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    }
                )
            }
        }
    ) { innerPadding ->
        HealthAppContentNavHost(
            navController = navController,
            healthConnectManager = healthConnectManager,
            modifier = Modifier.padding(innerPadding)
        )
    }
}