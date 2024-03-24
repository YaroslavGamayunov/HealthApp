package com.yaroslavgamayunov.healthapp.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.yaroslavgamayunov.healthapp.presentation.Screen
import com.yaroslavgamayunov.healthapp.presentation.navigateSingleTopTo

@Composable
fun LoginScreen(rootNavController: NavHostController) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            var emailValue by remember {
                mutableStateOf("")
            }
            TextField(
                singleLine = true,
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                value = emailValue,
                keyboardActions = KeyboardActions.Default,
                onValueChange = {
                    emailValue = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            var passwordValue by remember {
                mutableStateOf("")
            }

            var passwordVisibility: Boolean by remember { mutableStateOf(false) }
            TextField(
                singleLine = true,
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            if (passwordVisibility) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            null
                        )
                    }
                },
                label = { Text("Пароль") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                value = passwordValue,
                keyboardActions = KeyboardActions.Default,
                onValueChange = {
                    passwordValue = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Button(
                onClick = {
                    rootNavController.navigateSingleTopTo(Screen.ContentScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
            ) {
                Text("Войти")
            }
        }
    }
}