package com.yaroslavgamayunov.healthapp.util

import kotlin.math.round

fun Float.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}