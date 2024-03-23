package com.yaroslavgamayunov.healthapp.presentation.activity.weight

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yaroslavgamayunov.healthapp.data.HealthConnectManager

class WeightViewModelFactory(
    private val healthConnectManager: HealthConnectManager,
    private val applicationContext: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeightViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeightViewModel(
                healthConnectManager = healthConnectManager,
                applicationContext = applicationContext
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}