package com.yaroslavgamayunov.healthapp.presentation.activity.calories

import java.util.UUID

sealed class CaloriesUiState {
    data object Uninitialized : CaloriesUiState()
    data object Done : CaloriesUiState()

    // A random UUID is used in each Error object to allow errors to be uniquely identified,
    // and recomposition won't result in multiple snackbars.
    data class Error(val exception: Throwable, val uuid: UUID = UUID.randomUUID()) : CaloriesUiState()
}