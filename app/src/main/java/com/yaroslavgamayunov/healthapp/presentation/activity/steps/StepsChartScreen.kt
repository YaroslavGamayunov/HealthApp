package com.yaroslavgamayunov.healthapp.presentation.activity.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.yaroslavgamayunov.healthapp.R
import com.yaroslavgamayunov.healthapp.presentation.activity.ActivityChart
import com.yaroslavgamayunov.healthapp.presentation.activity.ActivityProgress
import com.yaroslavgamayunov.healthapp.presentation.goals.GoalSlider
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepsChartScreen(
    viewModel: StepsViewModel,
    navController: NavHostController,
    onPermissionsResult: () -> Unit = {},
    onPermissionsLaunch: (Set<String>) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.steps_screen)) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        },
        content = { innerPadding ->
            // Remember the last error ID, such that it is possible to avoid re-launching the error
            // notification for the same error when the screen is recomposed, or configuration changes etc.
            val errorId = rememberSaveable { mutableStateOf(UUID.randomUUID()) }

            val uiState = viewModel.uiState
            LaunchedEffect(uiState) {
                // If the initial data load has not taken place, attempt to load the data.
                if (uiState is StepsUiState.Uninitialized) {
                    onPermissionsResult()
                }

                // The [ExerciseSessionViewModel.UiState] provides details of whether the last action was a
                // success or resulted in an error. Where an error occurred, for example in reading and
                // writing to Health Connect, the user is notified, and where the error is one that can be
                // recovered from, an attempt to do so is made.
                if (uiState is StepsUiState.Error && errorId.value != uiState.uuid) {
//                    onError(uiState.exception) todo
                    errorId.value = uiState.uuid
                }
            }

            if (viewModel.uiState != StepsUiState.Uninitialized) {
                LazyColumn(modifier = Modifier.padding(innerPadding)) {
                    if (!viewModel.permissionsGranted) {
                        item {
                            Button(
                                onClick = {
                                    onPermissionsLaunch(viewModel.permissions)
                                }
                            ) {
                                Text(text = stringResource(R.string.permissions_button_label))
                            }
                        }
                    } else {
                        item {
                            ActivityChart(viewModel.stepsList, onItemClick = { index ->
                                viewModel.onChartItemClick(index)
                            })
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = {
                                    viewModel.selectPrevDay()
                                }) {
                                    Icon(Icons.AutoMirrored.Filled.NavigateBefore, null)
                                }
                                Text(viewModel.selectedDay)
                                IconButton(onClick = {
                                    viewModel.selectNextDay()
                                }) {
                                    Icon(Icons.AutoMirrored.Filled.NavigateNext, null)
                                }
                            }
                        }

                        if (viewModel.isShowingGoalEdit) {
                            item {
                                Column(modifier = Modifier.padding(all = 16.dp)) {
                                    Text(
                                        "Установить цель",
                                        modifier = Modifier.padding(
                                            start = 16.dp,
                                            bottom = 8.dp
                                        ),
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    GoalSlider(
                                        initialValue = viewModel.stepsGoal.toFloat(),
                                        goalText = { value ->
                                            "${value.toInt()} шагов"
                                        },
                                        maxValue = 30000f,
                                        onSave = { goal ->
                                            viewModel.saveGoal(goal.toInt())
                                        }
                                    )
                                }
                            }
                        }

                        viewModel.progressUiDto?.let {
                            item {
                                ActivityProgress(
                                    modifier = Modifier.padding(all = 16.dp),
                                    uiDto = it,
                                    onChangeGoalClick = {
                                        viewModel.onEditGoalClick()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}