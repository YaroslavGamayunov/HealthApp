package com.yaroslavgamayunov.healthapp.presentation.activity.weight

import android.content.Context
import android.os.RemoteException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SpeedRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.units.Mass
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaroslavgamayunov.healthapp.data.HealthConnectManager
import com.yaroslavgamayunov.healthapp.presentation.activity.ActivityProgressUiDto
import com.yaroslavgamayunov.healthapp.util.round
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.Instant
import java.time.temporal.ChronoUnit

class WeightViewModel(
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
        HealthPermission.getWritePermission(HeartRateRecord::class)
    )

    var permissionsGranted by mutableStateOf(false)
        private set

    var weightList: Map<Instant, Float> by mutableStateOf(mapOf())
        private set

    var weightGoal: Float by mutableFloatStateOf(90f) // todo load goal
        private set

    var isShowingGoalEdit: Boolean by mutableStateOf(false)
        private set

    var uiState: WeightUiState by mutableStateOf(WeightUiState.Uninitialized)
        private set

    var progressUiDto: ActivityProgressUiDto? by mutableStateOf(null)
        private set

    private var now = Instant.now().truncatedTo(ChronoUnit.DAYS)

    val permissionsLauncher = healthConnectManager.requestPermissionsActivityContract()

    fun initialLoad() {
        viewModelScope.launch {
            tryWithPermissionsCheck {
                readWeightData()
                val lastWeight = weightList.values.lastOrNull() ?: 0f
                progressUiDto = ActivityProgressUiDto(
                    value = "$lastWeight", // todo put into strings.xml
                    goal = "$weightGoal кг", // todo get goal from firebase/from health connect
                    progress = lastWeight / weightGoal // todo get goal from firebase/health connect
                )
            }
        }
    }

    fun onEditGoalClick() {
        isShowingGoalEdit = true
    }

    fun saveGoal(newGoal: Float) {
        // todo save to firebase
        weightGoal = newGoal
        isShowingGoalEdit = false
        val lastWeight = weightList.entries.last().value
        progressUiDto = progressUiDto?.copy(
            goal = "${weightGoal.round(1)} кг", // todo
            progress = lastWeight / weightGoal // todo get goal from firebase/health connect
        )
    }

    fun addWeightData(date: Instant, weightString: String) {
        viewModelScope.launch {
            try {
                val weightFloat = weightString.toFloat()
                healthConnectManager.writeWeightInput(
                    WeightRecord(
                        time = date,
                        zoneOffset = null,
                        weight = Mass.kilograms(weightFloat.toDouble())
                    )
                )

                weightList = weightList.toMutableMap().also { it[date] = weightFloat }
            } catch (_: NumberFormatException) {
            }
        }
    }

    private suspend fun readWeightData() {
        val weightData =
            healthConnectManager.readWeightInputs(start = now.minus(30, ChronoUnit.DAYS), now)

        weightList = weightData.associateBy(
            keySelector = {
                it.time
            },
            valueTransform = {
                it.weight.inKilograms.toFloat()
            }
        )
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
            WeightUiState.Done
        } catch (remoteException: RemoteException) {
            WeightUiState.Error(remoteException)
        } catch (securityException: SecurityException) {
            WeightUiState.Error(securityException)
        } catch (ioException: IOException) {
            WeightUiState.Error(ioException)
        } catch (illegalStateException: IllegalStateException) {
            WeightUiState.Error(illegalStateException)
        }
    }
}