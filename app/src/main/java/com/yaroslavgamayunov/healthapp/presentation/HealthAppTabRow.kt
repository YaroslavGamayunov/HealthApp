package com.yaroslavgamayunov.healthapp.presentation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource

@Composable
fun HealthAppTabRow(
    allScreens: List<HealthAppDestination>,
    onTabSelected: (HealthAppDestination) -> Unit,
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        allScreens.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.labelRes)) },
                selected = selectedItem == index,
                onClick = {
                    onTabSelected(screen)
                    selectedItem = index
                }
            )
        }
    }
}
