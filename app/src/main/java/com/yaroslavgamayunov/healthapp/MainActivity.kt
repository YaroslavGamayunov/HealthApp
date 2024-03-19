package com.yaroslavgamayunov.healthapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.yaroslavgamayunov.healthapp.presentation.HealthAppNavHost
import com.yaroslavgamayunov.healthapp.presentation.HealthAppTabRow
import com.yaroslavgamayunov.healthapp.presentation.healthAppTabRowScreens
import com.yaroslavgamayunov.healthapp.presentation.navigateSingleTopTo
import com.yaroslavgamayunov.healthapp.ui.theme.HealthAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        HealthAppTabRow(
                            allScreens = healthAppTabRowScreens,
                            onTabSelected = { newScreen ->
                                navController.navigateSingleTopTo(newScreen.route)
                            }
                        )
                    }
                ) { innerPadding ->
                    HealthAppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}