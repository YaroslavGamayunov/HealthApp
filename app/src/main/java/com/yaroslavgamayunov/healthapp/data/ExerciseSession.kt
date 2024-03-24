package com.yaroslavgamayunov.healthapp.data

import java.time.Instant

/**
 * Represents an exercise session.
 */
data class ExerciseSession(
    val startTime: Instant,
    val endTime: Instant,
    val id: String,
    val title: String?,
    val distance: String?,
    val calories: String?,
)