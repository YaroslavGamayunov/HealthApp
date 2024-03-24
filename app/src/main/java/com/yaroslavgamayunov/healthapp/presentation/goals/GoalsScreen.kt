package com.yaroslavgamayunov.healthapp.presentation.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yaroslavgamayunov.healthapp.R
import com.yaroslavgamayunov.healthapp.util.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.goals_screen)) }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp),
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.DirectionsWalk,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .background(
                                        shape = CircleShape,
                                        color = Color.Green,
                                    )
                                    .padding(4.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Шаги",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        var initialSteps by remember {
                            mutableFloatStateOf(5000f)
                        }
                        GoalSlider(
                            initialValue = initialSteps, // todo get from firebase
                            maxValue = 30000f,
                            onSave = { steps ->
                                initialSteps = steps
                            },
                            goalText = { value ->
                                "${value.toInt()} шагов"
                            }
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(
                                bottom = 8.dp,
                                top = 32.dp
                            ),
                        ) {
                            Icon(
                                Icons.Filled.LocalFireDepartment,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .background(
                                        shape = CircleShape,
                                        color = Color.Red,
                                    )
                                    .padding(4.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Калории",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        var initialCalories by remember {
                            mutableFloatStateOf(5000f)
                        }
                        GoalSlider(
                            initialValue = initialCalories, // todo get from firebase
                            maxValue = 5000f,
                            onSave = { calories ->
                                initialCalories = calories
                            },
                            goalText = { value ->
                                "${value.toInt()} калорий"
                            }
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(
                                bottom = 8.dp,
                                top = 32.dp
                            ),
                        ) {
                            Icon(
                                Icons.Filled.MonitorWeight,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .background(
                                        shape = CircleShape,
                                        color = Color.Cyan,
                                    )
                                    .padding(4.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Вес",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        var initialWeight by remember {
                            mutableFloatStateOf(5000f)
                        }
                        GoalSlider(
                            initialValue = initialWeight,
                            goalText = { value ->
                                "${value.round(1)} кг"
                            },
                            maxValue = 200f,
                            onSave = { goal ->
                                initialWeight = goal
                            }
                        )
                    }
                }
            }
        })
}