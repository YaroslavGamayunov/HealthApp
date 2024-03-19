package com.yaroslavgamayunov.healthapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yaroslavgamayunov.healthapp.presentation.questions.QuestionsScreen
import com.yaroslavgamayunov.healthapp.presentation.goals.GoalsScreen
import com.yaroslavgamayunov.healthapp.presentation.home.HomeScreen
import com.yaroslavgamayunov.healthapp.presentation.profile.ProfileScreen

@Composable
fun HealthAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen()
        }
        composable(route = Questions.route) {
            QuestionsScreen()
        }
        composable(route = Goals.route) {
            GoalsScreen()
        }
        composable(route = Profile.route) {
            ProfileScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }