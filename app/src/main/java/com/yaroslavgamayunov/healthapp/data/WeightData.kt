package com.yaroslavgamayunov.healthapp.data

import androidx.health.connect.client.units.Mass
import java.time.ZonedDateTime

/**
 * Represents a weight record and associated data.
 */
data class WeightData(
    val weight: Mass,
    val id: String,
    val time: ZonedDateTime,
    val sourceAppInfo: HealthConnectAppInfo?
)