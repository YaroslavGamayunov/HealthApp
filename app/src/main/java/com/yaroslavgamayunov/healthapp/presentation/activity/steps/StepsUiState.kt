package com.yaroslavgamayunov.healthapp.presentation.activity.steps

import java.util.UUID

sealed class StepsUiState {
    data object Uninitialized : StepsUiState()
    data object Done : StepsUiState()

    // A random UUID is used in each Error object to allow errors to be uniquely identified,
    // and recomposition won't result in multiple snackbars.
    data class Error(val exception: Throwable, val uuid: UUID = UUID.randomUUID()) : StepsUiState()
}