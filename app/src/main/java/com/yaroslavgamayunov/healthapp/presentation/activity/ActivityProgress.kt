package com.yaroslavgamayunov.healthapp.presentation.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable

fun ActivityProgress(
    modifier: Modifier = Modifier,
    uiDto: ActivityProgressUiDto,
    onChangeGoalClick: () -> Unit,
) {
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp)
        ) {
            Text("Достижение цели", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onChangeGoalClick) {
                Icon(Icons.Filled.Edit, null)
            }
        }

        Column(Modifier.padding(horizontal = 12.dp)) {
            Text(
                text = uiDto.value,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "/${uiDto.goal}",
                style = MaterialTheme.typography.titleLarge
            )

            LinearProgressIndicator(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(
                        RoundedCornerShape(16.dp)
                    ),
                trackColor = Color.White,
                progress = {
                    uiDto.progress
                }
            )
        }
    }
}

data class ActivityProgressUiDto(
    val value: String,
    val goal: String,
    val progress: Float,
)

@Preview(widthDp = 360)
@Composable
private fun PreviewActivityProgress() {
    ActivityProgress(
        uiDto = ActivityProgressUiDto(
            value = "500",
            goal = "5000 Шагов",
            progress = 0.25f
        ),
        onChangeGoalClick = {}
    )
}