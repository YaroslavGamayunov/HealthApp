package com.yaroslavgamayunov.healthapp.presentation.activity.weight

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.yaroslavgamayunov.healthapp.R
import com.yaroslavgamayunov.healthapp.presentation.activity.ActivityProgress
import com.yaroslavgamayunov.healthapp.presentation.goals.GoalSlider
import com.yaroslavgamayunov.healthapp.util.round
import java.time.Instant
import java.util.Date
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightChartScreen(
    viewModel: WeightViewModel,
    navController: NavHostController,
    onPermissionsResult: () -> Unit = {},
    onPermissionsLaunch: (Set<String>) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.weight_screen)) },
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
            val errorId = rememberSaveable { mutableStateOf(UUID.randomUUID()) }

            val uiState = viewModel.uiState
            LaunchedEffect(uiState) {
                // If the initial data load has not taken place, attempt to load the data.
                if (uiState is WeightUiState.Uninitialized) {
                    onPermissionsResult()
                }

                if (uiState is WeightUiState.Error && errorId.value != uiState.uuid) {
//                    onError(uiState.exception) todo
                    errorId.value = uiState.uuid
                }
            }

            if (viewModel.uiState != WeightUiState.Uninitialized) {
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
                            WeightChart(
                                goal = viewModel.weightGoal,
                                data = viewModel.weightList,
                                modifier = Modifier.padding(all = 16.dp)
                            )
                        }


                        item {
                            Text(
                                "Добавить запись",
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    bottom = 8.dp
                                ),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        item {
                            var addWeightValue by remember {
                                mutableStateOf("")
                            }

                            Card(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    singleLine = true,
                                    label = { Text("Вес (кг)") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    value = addWeightValue,
                                    keyboardActions = KeyboardActions.Default,
                                    onValueChange = {
                                        addWeightValue = it
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )

                                val datePickerState = rememberDatePickerState(
                                    initialSelectedDateMillis = Instant.now().toEpochMilli()
                                )
                                var datePickerOpen by remember {
                                    mutableStateOf(false)
                                }
                                if (datePickerOpen) {
                                    DatePickerDialog(
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        onDismissRequest = {
                                            datePickerOpen = false
                                        },
                                        confirmButton = {
                                            TextButton(
                                                onClick = {
                                                    datePickerOpen = false
                                                }
                                            ) {
                                                Text("OK")
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(
                                                onClick = {
                                                    datePickerOpen = false
                                                }
                                            ) {
                                                Text("Cancel")
                                            }
                                        }
                                    ) {
                                        DatePicker(state = datePickerState)
                                    }
                                }

                                OutlinedButton(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    onClick = {
                                        datePickerOpen = true
                                    },
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.CalendarMonth,
                                            contentDescription = null
                                        )

                                        Spacer(modifier = Modifier.width(4.dp))

                                        Text(
                                            DateFormat.format(
                                                "dd MMMM yyyy", Date.from(
                                                    datePickerState.selectedDateMillis?.let {
                                                        Instant.ofEpochMilli(it)
                                                    } ?: Instant.now()
                                                )
                                            ).toString()
                                        )
                                    }
                                }

                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    onClick = {
                                        viewModel.addWeightData(
                                            date = datePickerState.selectedDateMillis?.let {
                                                Instant.ofEpochMilli(it)
                                            } ?: Instant.now(),
                                            weightString = addWeightValue
                                        )
                                    }
                                ) {
                                    Text("Добавить")
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
                                        initialValue = viewModel.weightGoal,
                                        goalText = { value ->
                                            "${value.round(1)} кг"
                                        },
                                        maxValue = 200f,
                                        onSave = { goal ->
                                            viewModel.saveGoal(goal)
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