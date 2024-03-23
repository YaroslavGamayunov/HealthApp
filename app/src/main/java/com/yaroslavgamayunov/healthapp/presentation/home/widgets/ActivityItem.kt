package com.yaroslavgamayunov.healthapp.presentation.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ActivityItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    iconBackgroundColor: Color,
    icon: ImageVector,
    status: String,
    goal: String,
    progress: Float,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(all = 10.dp)
        ) {

            Icon(
                icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .background(
                        shape = CircleShape,
                        color = iconBackgroundColor
                    )
                    .padding(4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = status, style = MaterialTheme.typography.headlineSmall)
            Text(goal)
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(progress = { progress }, modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(
                    RoundedCornerShape(16.dp)
                ),
                trackColor = Color.White,)
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
            title = "Шаги",
            icon = Icons.Filled.SportsTennis,
            goal = "/15000 Шагов",
            status = "5349",
            iconBackgroundColor = Color.Cyan,
            progress = 0.34f,
            onClick = {}
        )

        ActivityItem(
            modifier = Modifier.weight(1.0f),
            title = "Шаги",
            icon = Icons.Filled.SportsTennis,
            goal = "Goal: 15000 Steps",
            status = "5349",
            iconBackgroundColor = Color.Red,
            progress = 0.34f,
            onClick = {}
        )
    }
}