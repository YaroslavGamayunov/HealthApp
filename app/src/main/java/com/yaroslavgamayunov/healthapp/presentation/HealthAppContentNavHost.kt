package com.yaroslavgamayunov.healthapp.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yaroslavgamayunov.healthapp.data.HealthConnectManager
import com.yaroslavgamayunov.healthapp.presentation.activity.calories.CaloriesChartScreen
import com.yaroslavgamayunov.healthapp.presentation.activity.calories.CaloriesViewModel
import com.yaroslavgamayunov.healthapp.presentation.activity.calories.CaloriesViewModelFactory
import com.yaroslavgamayunov.healthapp.presentation.activity.steps.StepsChartScreen
import com.yaroslavgamayunov.healthapp.presentation.activity.steps.StepsViewModel
import com.yaroslavgamayunov.healthapp.presentation.activity.steps.StepsViewModelFactory
import com.yaroslavgamayunov.healthapp.presentation.activity.weight.WeightChartScreen
import com.yaroslavgamayunov.healthapp.presentation.activity.weight.WeightViewModel
import com.yaroslavgamayunov.healthapp.presentation.activity.weight.WeightViewModelFactory
import com.yaroslavgamayunov.healthapp.presentation.goals.GoalsScreen
import com.yaroslavgamayunov.healthapp.presentation.home.HomeScreen
import com.yaroslavgamayunov.healthapp.presentation.profile.ProfileScreen

@Composable
fun HealthAppContentNavHost(
    rootNavController: NavController,
    navController: NavHostController,
    healthConnectManager: HealthConnectManager,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen(
                rootNavController = rootNavController,
                navController = navController
            )
        }
        composable(route = Goals.route) {
            GoalsScreen(
                navController = navController
            )
        }
        composable(route = Profile.route) {
            ProfileScreen(rootNavController = rootNavController)
        }
        composable(
            route = ActivityChart.routeWithArgs,
            arguments = ActivityChart.arguments
        ) { navBackStackEntry ->
            val activityTypeString =
                navBackStackEntry.arguments?.getString(ActivityChart.activityTypeArg)
            val activityType = activityTypeString?.let(ActivityChart.Type::valueOf)!!

            when (activityType) {
                ActivityChart.Type.Steps -> StepsChartContent(navController, healthConnectManager)
                ActivityChart.Type.Weight -> WeightChartContent(navController, healthConnectManager)
                ActivityChart.Type.Calories -> CaloriesChartContent(
                    navController,
                    healthConnectManager
                )
            }
        }
    }
}

@Composable
fun StepsChartContent(
    navController: NavHostController,
    healthConnectManager: HealthConnectManager,
) {
    val viewModel: StepsViewModel = viewModel(
        factory = StepsViewModelFactory(
            healthConnectManager = healthConnectManager,
            applicationContext = LocalContext.current.applicationContext
        )
    )
    val onPermissionsResult = { viewModel.initialLoad() }
    val permissionsLauncher =
        rememberLauncherForActivityResult(viewModel.permissionsLauncher) {
            onPermissionsResult()
        }
    StepsChartScreen(
        viewModel = viewModel,
        navController = navController,
        onPermissionsResult = onPermissionsResult,
        onPermissionsLaunch = { values ->
            permissionsLauncher.launch(values)
        })
}

@Composable
fun CaloriesChartContent(
    navController: NavHostController,
    healthConnectManager: HealthConnectManager,
) {
    val viewModel: CaloriesViewModel = viewModel(
        factory = CaloriesViewModelFactory(
            healthConnectManager = healthConnectManager,
            applicationContext = LocalContext.current.applicationContext
        )
    )
    val onPermissionsResult = { viewModel.initialLoad() }
    val permissionsLauncher =
        rememberLauncherForActivityResult(viewModel.permissionsLauncher) {
            onPermissionsResult()
        }
    CaloriesChartScreen(
        viewModel = viewModel,
        navController = navController,
        onPermissionsResult = onPermissionsResult,
        onPermissionsLaunch = { values ->
            permissionsLauncher.launch(values)
        })
}

@Composable
fun WeightChartContent(
    navController: NavHostController,
    healthConnectManager: HealthConnectManager,
) {
    val viewModel: WeightViewModel = viewModel(
        factory = WeightViewModelFactory(
            healthConnectManager = healthConnectManager,
            applicationContext = LocalContext.current.applicationContext
        )
    )
    val onPermissionsResult = { viewModel.initialLoad() }
    val permissionsLauncher =
        rememberLauncherForActivityResult(viewModel.permissionsLauncher) {
            onPermissionsResult()
        }
    WeightChartScreen(
        viewModel = viewModel,
        navController = navController,
        onPermissionsResult = onPermissionsResult,
        onPermissionsLaunch = { values ->
            permissionsLauncher.launch(values)
        })
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

fun NavHostController.navigateToActivityChart(activityType: ActivityChart.Type) {
    this.navigate("${ActivityChart.route}/$activityType")
}