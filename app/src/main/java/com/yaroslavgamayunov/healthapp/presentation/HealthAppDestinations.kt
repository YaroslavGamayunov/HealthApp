package com.yaroslavgamayunov.healthapp.presentation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.yaroslavgamayunov.healthapp.R

/**
 * Contract for information needed on every HealthApp navigation destination
 */

interface HealthAppDestination {
    val icon: ImageVector
    val route: String

    @get:StringRes
    val labelRes: Int
}

/**
 * HealthApp navigation destinations
 */
object Home : HealthAppDestination {
    override val icon = Icons.Filled.Favorite
    override val route = "home"
    override val labelRes = R.string.home_screen
}

object Questions : HealthAppDestination {
    override val icon = Icons.Filled.QuestionAnswer
    override val route = "questions"
    override val labelRes = R.string.questions_screen
}

object Goals : HealthAppDestination {
    override val icon = Icons.AutoMirrored.Filled.ShowChart
    override val route = "goals"
    override val labelRes = R.string.goals_screen
}

object Profile : HealthAppDestination {
    override val icon = Icons.Filled.Person
    override val route = "profile"
    override val labelRes = R.string.profile_screen
}

object ActivityChart {
    enum class Type {
        Steps, Weight, Calories
    }

    const val route = "activity"
    const val activityTypeArg = "activity_type"
    val routeWithArgs = "$route/{$activityTypeArg}"
    val arguments = listOf(
        navArgument(activityTypeArg) {
            type = NavType.StringType
        }
    )
}


// Screens to be displayed in the top HealthAppTabRow
val healthAppTabRowScreens = listOf(Home, Goals, Profile)
