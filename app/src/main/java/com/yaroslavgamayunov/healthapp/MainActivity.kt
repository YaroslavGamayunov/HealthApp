package com.yaroslavgamayunov.healthapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.yaroslavgamayunov.healthapp.presentation.HealthRootNavHost
import com.yaroslavgamayunov.healthapp.presentation.theme.HealthAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthAppTheme {
                HealthRootNavHost(
                    navController = rememberNavController(),
                    healthConnectManager = (application as HealthAppApplication).healthConnectManager
                )
            }
        }
    }
}