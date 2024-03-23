package com.yaroslavgamayunov.healthapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.yaroslavgamayunov.healthapp.presentation.ActivityChart
import com.yaroslavgamayunov.healthapp.presentation.Questions
import com.yaroslavgamayunov.healthapp.presentation.home.widgets.ActivityItem
import com.yaroslavgamayunov.healthapp.presentation.home.widgets.QuestionsItem
import com.yaroslavgamayunov.healthapp.presentation.navigateToActivityChart

@Composable
fun HomeScreen(navController: NavHostController) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 120.dp),
        contentPadding = PaddingValues(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Text("Привет, Ярослав!", style = MaterialTheme.typography.displayMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Сегодня ты молодец!", style = MaterialTheme.typography.titleLarge)
            }
        }
        item {
            QuestionsItem(
                title = "Задать вопросы ИИ-помощнику",
                subTitle = "Получите полезные ответы о здоровье",
                onClick = {
                    navController.navigate(Questions.route)
                }
            )
        }
        items(2) {
            ActivityItem(
                title = "Вес",
                icon = Icons.Filled.SportsTennis,
                goal = "/2000 калорий",
                status = "500 калорий",
                progress = 0.34f,
                iconBackgroundColor = Color.Cyan,
                onClick = {
                    navController.navigateToActivityChart(ActivityChart.Type.Calories)
                }
            )
        }
        items(2) {
            ActivityItem(
                title = "Вес",
                icon = Icons.Filled.SportsTennis,
                goal = "/40 кг",
                status = "20",
                progress = 0.34f,
                iconBackgroundColor = Color.Green,
                onClick = {
                    navController.navigateToActivityChart(ActivityChart.Type.Weight)
                }
            )
        }
        items(2) {
            ActivityItem(
                title = "Steps",
                icon = Icons.Filled.SportsTennis,
                goal = "Goal: 15000 Steps",
                status = "5349",
                progress = 0.34f,
                iconBackgroundColor = Color.Green,
                onClick = {
                    navController.navigateToActivityChart(ActivityChart.Type.Steps)
                }
            )
        }
    }
}