package com.yaroslavgamayunov.healthapp.presentation.activity.weight

import java.util.UUID

sealed class WeightUiState {
    data object Uninitialized : WeightUiState()
    data object Done : WeightUiState()

    // A random UUID is used in each Error object to allow errors to be uniquely identified,
    // and recomposition won't result in multiple snackbars.
    data class Error(val exception: Throwable, val uuid: UUID = UUID.randomUUID()) : WeightUiState()
}