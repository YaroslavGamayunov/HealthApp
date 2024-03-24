package com.yaroslavgamayunov.healthapp.presentation

import com.yaroslavgamayunov.healthapp.R

enum class Screen(val route: String, val titleId: Int, val hasMenuItem: Boolean = true) {
    RegistrationScreen("registration_screen", R.string.registration_screen, false),
    WelcomeScreen("welcome_screen", R.string.welcome_screen, false),
    ContentScreen("content_screen", 0, false),
    LoginScreen("login_screen", 0, false)
}