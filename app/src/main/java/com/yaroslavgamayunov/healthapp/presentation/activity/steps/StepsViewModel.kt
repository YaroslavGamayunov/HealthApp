package com.yaroslavgamayunov.healthapp.presentation.activity.steps

import android.content.Context
import android.os.RemoteException
import android.text.format.DateFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SpeedRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaroslavgamayunov.healthapp.data.HealthConnectManager
import com.yaroslavgamayunov.healthapp.presentation.activity.ActivityItemUiDto
import com.yaroslavgamayunov.healthapp.presentation.activity.ActivityProgressUiDto
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

class StepsViewModel(
    private val healthConnectManager: HealthConnectManager,
    private val applicationContext: Context,
) :
    ViewModel() {
    private val healthConnectCompatibleApps = healthConnectManager.healthConnectCompatibleApps

    val permissions = setOf(
        HealthPermission.getWritePermission(ExerciseSessionRecord::class),
        HealthPermission.getReadPermission(ExerciseSessionRecord::class),
        HealthPermission.getWritePermission(StepsRecord::class),
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getWritePermission(SpeedRecord::class),
        HealthPermission.getWritePermission(DistanceRecord::class),
        HealthPermission.getWritePermission(TotalCaloriesBurnedRecord::class),
        HealthPermission.getWritePermission(HeartRateRecord::class)
    )

    var permissionsGranted by mutableStateOf(false)
        private set

    var stepsList: List<ActivityItemUiDto> by mutableStateOf(listOf())
        private set

    var stepsGoal: Int by mutableIntStateOf(5000) // todo load goal
        private set

    var isShowingGoalEdit: Boolean by mutableStateOf(false)
        private set

    var selectedDay: String by mutableStateOf("")
        private set

    var uiState: StepsUiState by mutableStateOf(StepsUiState.Uninitialized)
        private set

    var progressUiDto: ActivityProgressUiDto? by mutableStateOf(null)
        private set

    private var now = Instant.now().truncatedTo(ChronoUnit.DAYS)

    private var selectedIndex = 6

    val permissionsLauncher = healthConnectManager.requestPermissionsActivityContract()

    fun initialLoad() {
        viewModelScope.launch {
            tryWithPermissionsCheck {
                readStepsData()
                setSelectedItem(selectedIndex)
            }
        }
    }

    private fun setSelectedItem(index: Int) {
        val updatedStepsList = stepsList.toMutableList()
        updatedStepsList[selectedIndex] = updatedStepsList[selectedIndex].copy(isSelected = false)
        updatedStepsList[index] = updatedStepsList[index].copy(isSelected = true)
        selectedIndex = index
        selectedDay = updatedStepsList[index].longDate
        stepsList = updatedStepsList
        progressUiDto = ActivityProgressUiDto(
            value = updatedStepsList[index].value.toInt().toString(), // todo put into strings.xml
            goal = "$stepsGoal шагов", // todo get goal from firebase/from health connect
            progress = updatedStepsList[index].value / stepsGoal // todo get goal from firebase/health connect
        )
    }

    fun selectNextDay() {
        if (selectedIndex == stepsList.lastIndex) {
            val next = now.plus(1, ChronoUnit.DAYS)
            if (Instant.now().isBefore(next)) {
                return
            }
            now = next
            viewModelScope.launch {
                readStepsData()
                setSelectedItem((selectedIndex + 1).coerceAtMost(6))
            }
        } else {
            setSelectedItem(selectedIndex + 1)
        }
    }

    fun selectPrevDay() {
        if (selectedIndex == 0) {
            now = now.minus(1, ChronoUnit.DAYS)
            viewModelScope.launch {
                readStepsData()
                setSelectedItem(0)
            }
        } else {
            setSelectedItem(selectedIndex - 1)
        }
    }

    fun onChartItemClick(index: Int) {
        setSelectedItem(index)
    }

    fun onEditGoalClick() {
        isShowingGoalEdit = true
    }

    fun saveGoal(newGoal: Int) {
        // todo save to firebase
        stepsGoal = newGoal
        isShowingGoalEdit = false
        progressUiDto = progressUiDto?.copy(
            goal = "$stepsGoal шагов", // todo
            progress = stepsList[selectedIndex].value / newGoal // todo get goal from firebase/health connect
        )
    }

    private suspend fun readStepsData() {
        val stepsData = healthConnectManager.aggregateStepsInLastWeek(now)
        val maxSteps = stepsData.maxOf { it.second ?: 0 }.toFloat().coerceAtLeast(1f)

        stepsList = stepsData
            .map { (dayStart, steps) ->
                ActivityItemUiDto(
                    normalizedValue = (steps ?: 0) / maxSteps, // todo
                    value = steps?.toFloat() ?: 0f,
                    dayOfMonth = DateFormat.format("dd", Date.from(dayStart)).toString(),
                    dayOfWeek = DateFormat.format("EEE", Date.from(dayStart)).toString(),
                    longDate = DateFormat.getLongDateFormat(applicationContext)
                        .format(Date.from(dayStart)),
                    isSelected = false
                )
            }
    }

    /**
     * Provides permission check and error handling for Health Connect suspend function calls.
     *
     * Permissions are checked prior to execution of [block], and if all permissions aren't granted
     * the [block] won't be executed, and [permissionsGranted] will be set to false, which will
     * result in the UI showing the permissions button.
     *
     * Where an error is caught, of the type Health Connect is known to throw, [uiState] is set to
     * [UiState.Error], which results in the snackbar being used to show the error message.
     */
    private suspend fun tryWithPermissionsCheck(block: suspend () -> Unit) {
        permissionsGranted = healthConnectManager.hasAllPermissions(permissions)
        uiState = try {
            if (permissionsGranted) {
                block()
            }
            StepsUiState.Done
        } catch (remoteException: RemoteException) {
            StepsUiState.Error(remoteException)
        } catch (securityException: SecurityException) {
            StepsUiState.Error(securityException)
        } catch (ioException: IOException) {
            StepsUiState.Error(ioException)
        } catch (illegalStateException: IllegalStateException) {
            StepsUiState.Error(illegalStateException)
        }
    }
}