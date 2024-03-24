package com.yaroslavgamayunov.healthapp.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yaroslavgamayunov.healthapp.R
import com.yaroslavgamayunov.healthapp.presentation.Screen
import com.yaroslavgamayunov.healthapp.presentation.navigateSingleTopTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(rootNavController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.profile_screen)) }
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                "Ярослав Гамаюнов",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                "Учетная запись",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium
            )
            Text("yarik2550@yandex.ru", modifier = Modifier.padding(16.dp))
            Button(
                onClick = {
                    (rootNavController as NavHostController).navigateSingleTopTo(Screen.LoginScreen.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Выйти")
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 700)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(rememberNavController())
}