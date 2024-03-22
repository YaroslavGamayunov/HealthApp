package com.yaroslavgamayunov.healthapp.presentation.activity.steps

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yaroslavgamayunov.healthapp.data.HealthConnectManager

class StepsViewModelFactory(
    private val healthConnectManager: HealthConnectManager,
    private val applicationContext: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StepsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StepsViewModel(
                healthConnectManager = healthConnectManager,
                applicationContext = applicationContext
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}