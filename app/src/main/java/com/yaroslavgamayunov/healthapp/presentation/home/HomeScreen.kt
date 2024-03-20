package com.yaroslavgamayunov.healthapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yaroslavgamayunov.healthapp.presentation.home.widgets.ActivityItem

@Composable
fun HomeScreen() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(span = { GridItemSpan(3) }) {
            Column {
                Text("Hello, Yaroslav!", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Text("You are doing good today!")
            }
        }
        items(20) {
            ActivityItem(
                title = "Steps",
                icon = Icons.Filled.SportsTennis,
                goal = "Goal: 15000 Steps",
                status = "5349",
                progress = 0.34f
            )
        }
    }
}