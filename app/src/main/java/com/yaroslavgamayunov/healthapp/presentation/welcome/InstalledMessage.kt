package com.yaroslavgamayunov.healthapp.presentation.welcome

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.yaroslavgamayunov.healthapp.R

/**
 * Welcome text shown when the app first starts, where the Healthcore APK is already installed.
 */
@Composable
fun InstalledMessage() {
    Text(
        text = stringResource(id = R.string.installed_welcome_message),
        textAlign = TextAlign.Justify
    )
}