package com.yaroslavgamayunov.healthapp.presentation.home.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ActivityItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    status: String,
    goal: String,
    progress: Float,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Text(text = title)
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Icon(icon, contentDescription = null)
                Text(status)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(goal)
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(progress = { progress })
        }
    }
}

@Preview(
    widthDp = 360
)
@Composable
private fun PreviewActivityItem() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ActivityItem(
            modifier = Modifier.weight(1.0f),
            title = "Steps",
            icon = Icons.Filled.SportsTennis,
            goal = "Goal: 15000 Steps",
            status = "5349",
            progress = 0.34f
        )

        ActivityItem(
            modifier = Modifier.weight(1.0f),
            title = "Steps",
            icon = Icons.Filled.SportsTennis,
            goal = "Goal: 15000 Steps",
            status = "5349",
            progress = 0.34f
        )
    }
}