package com.yaroslavgamayunov.healthapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.yaroslavgamayunov.healthapp.data.ExerciseSession
import com.yaroslavgamayunov.healthapp.presentation.ActivityChart
import com.yaroslavgamayunov.healthapp.presentation.Questions
import com.yaroslavgamayunov.healthapp.presentation.activity.excercise.ExerciseItem
import com.yaroslavgamayunov.healthapp.presentation.home.widgets.ActivityItem
import com.yaroslavgamayunov.healthapp.presentation.home.widgets.QuestionsItem
import com.yaroslavgamayunov.healthapp.presentation.navigateToActivityChart
import java.time.Instant

@Composable
fun HomeScreen(
    rootNavController: NavController,
    navController: NavHostController,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        contentPadding = PaddingValues(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Text("Привет, Ярослав!", style = MaterialTheme.typography.displayMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Сегодня ты молодец!", style = MaterialTheme.typography.titleLarge)
            }
        }
        item {
            QuestionsItem(
                title = "ИИ-помощник",
                subTitle = "Задайте любой вопрос о здоровье",
                onClick = {
                    rootNavController.navigate(Questions.route)
                }
            )
        }

        item {
            ActivityItem(
                title = "Вес",
                icon = Icons.Filled.MonitorWeight,
                goal = "Цель: 90 кг",
                status = "81 кг",
                progress = 0.34f,
                iconBackgroundColor = Color.Cyan,
                onClick = {
                    navController.navigateToActivityChart(ActivityChart.Type.Weight)
                }
            )
        }

        item {
            ActivityItem(
                title = "Шаги",
                icon = Icons.AutoMirrored.Filled.DirectionsWalk,
                goal = "/15000 шагов",
                status = "0",
                progress = 0/15000f,
                iconBackgroundColor = Color.Green,
                onClick = {
                    navController.navigateToActivityChart(ActivityChart.Type.Steps)
                }
            )
        }

        item {
            ActivityItem(
                title = "Калории",
                icon = Icons.Filled.LocalFireDepartment,
                goal = "/2000 калорий",
                status = "1563",
                progress = 1563/2000f,
                iconBackgroundColor = Color.Red,
                onClick = {
                    navController.navigateToActivityChart(ActivityChart.Type.Calories)
                }
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp, top = 16.dp),
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.DirectionsRun,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .background(
                                shape = CircleShape,
                                color = Color.Magenta
                            )
                            .padding(4.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Последние тренировки",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        )
                ) {
                    item {
                        val time2 = Instant.now()
                        val time1 = time2.minusSeconds(3600)

                        ExerciseItem(
                            item = ExerciseSession(
                                startTime = time1,
                                endTime = time2,
                                id = "",
                                title = "Ходьба",
                                distance = "10.4 км",
                                calories = "432 ккал"
                            )
                        )
                    }

                    item {
                        val time2 = Instant.now().minusSeconds(86400)
                        val time1 = time2.minusSeconds(3600)

                        ExerciseItem(
                            item = ExerciseSession(
                                startTime = time1,
                                endTime = time2,
                                id = "",
                                title = "Прогулка",
                                distance = "4 км",
                                calories = "129 ккал"
                            )
                        )
                    }

                    item {
                        val time2 = Instant.now().minusSeconds(83333)
                        val time1 = time2.minusSeconds(3600)

                        ExerciseItem(
                            item = ExerciseSession(
                                startTime = time1,
                                endTime = time2,
                                id = "",
                                title = "Спорт",
                                distance = "8 км",
                                calories = "600 ккал"
                            )
                        )
                    }
                }
            }
        }
    }
}