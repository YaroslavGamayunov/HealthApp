package com.yaroslavgamayunov.healthapp.presentation.activity.calories

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

class CaloriesViewModel(
    private val healthConnectManager: HealthConnectManager,
    private val applicationContext: Context,
) : ViewModel() {

    val permissions = setOf(
        HealthPermission.getWritePermission(ExerciseSessionRecord::class),
        HealthPermission.getReadPermission(ExerciseSessionRecord::class),
        HealthPermission.getWritePermission(StepsRecord::class),
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getWritePermission(SpeedRecord::class),
        HealthPermission.getWritePermission(DistanceRecord::class),
        HealthPermission.getWritePermission(TotalCaloriesBurnedRecord::class),
        HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class),
        HealthPermission.getWritePermission(HeartRateRecord::class)
    )

    var permissionsGranted by mutableStateOf(false)
        private set

    var caloriesList: List<ActivityItemUiDto> by mutableStateOf(listOf())
        private set

    var caloriesGoal: Int by mutableIntStateOf(5000) // todo load goal
        private set

    var isShowingGoalEdit: Boolean by mutableStateOf(false)
        private set

    var selectedDay: String by mutableStateOf("")
        private set

    var uiState: CaloriesUiState by mutableStateOf(CaloriesUiState.Uninitialized)
        private set

    var progressUiDto: ActivityProgressUiDto? by mutableStateOf(null)
        private set

    private var now = Instant.now().truncatedTo(ChronoUnit.DAYS)

    private var selectedIndex = 6

    val permissionsLauncher = healthConnectManager.requestPermissionsActivityContract()

    fun initialLoad() {
        viewModelScope.launch {
            tryWithPermissionsCheck {
                readCaloriesData()
                setSelectedItem(selectedIndex)
            }
        }
    }

    private fun setSelectedItem(index: Int) {
        val updatedCaloriesList = caloriesList.toMutableList()
        updatedCaloriesList[selectedIndex] =
            updatedCaloriesList[selectedIndex].copy(isSelected = false)
        updatedCaloriesList[index] = updatedCaloriesList[index].copy(isSelected = true)
        selectedIndex = index
        selectedDay = updatedCaloriesList[index].longDate
        caloriesList = updatedCaloriesList
        progressUiDto = ActivityProgressUiDto(
            value = updatedCaloriesList[index].value.toInt()
                .toString(), // todo put into strings.xml
            goal = "$caloriesGoal калорий", // todo get goal from firebase/from health connect
            progress = updatedCaloriesList[index].value / caloriesGoal // todo get goal from firebase/health connect
        )
    }

    fun selectNextDay() {
        if (selectedIndex == caloriesList.lastIndex) {
            val next = now.plus(1, ChronoUnit.DAYS)
            if (Instant.now().isBefore(next)) {
                return
            }
            now = next
            viewModelScope.launch {
                readCaloriesData()
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
                readCaloriesData()
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
        caloriesGoal = newGoal
        isShowingGoalEdit = false
        progressUiDto = progressUiDto?.copy(
            goal = "$caloriesGoal калорий", // todo
            progress = caloriesList[selectedIndex].value / newGoal // todo get goal from firebase/health connect
        )
    }

    private suspend fun readCaloriesData() {
        val caloriesData = healthConnectManager.aggregateCaloriesInLastWeek(now)
        val maxCalories = caloriesData.maxOf { it.second ?: 0 }.toFloat().coerceAtLeast(1f)

        caloriesList = caloriesData
            .map { (dayStart, calories) ->
                ActivityItemUiDto(
                    normalizedValue = (calories ?: 0) / maxCalories, // todo
                    value = calories?.toFloat() ?: 0f,
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
            CaloriesUiState.Done
        } catch (remoteException: RemoteException) {
            CaloriesUiState.Error(remoteException)
        } catch (securityException: SecurityException) {
            CaloriesUiState.Error(securityException)
        } catch (ioException: IOException) {
            CaloriesUiState.Error(ioException)
        } catch (illegalStateException: IllegalStateException) {
            CaloriesUiState.Error(illegalStateException)
        }
    }
}