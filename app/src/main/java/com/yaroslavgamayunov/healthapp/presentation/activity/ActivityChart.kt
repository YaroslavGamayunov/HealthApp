package com.yaroslavgamayunov.healthapp.presentation.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ActivityChart(items: List<ActivityItemUiDto>, onItemClick: (position: Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEachIndexed { index, uiDto ->
            ActivityItem(uiDto = uiDto, onClick = {
                onItemClick(index)
            })
        }
    }
}

data class ActivityItemUiDto(
    val normalizedValue: Float,
    val dayOfMonth: String,
    val dayOfWeek: String,
    val isSelected: Boolean,
)

@Composable
fun ActivityItem(
    uiDto: ActivityItemUiDto,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .background(
                color = if (uiDto.isSelected) MaterialTheme.colorScheme.outlineVariant else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        Box(modifier = Modifier.height(240.dp)) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxHeight(uiDto.normalizedValue)
                    .align(Alignment.BottomCenter)
                    .width(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier.padding(all = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(uiDto.dayOfMonth)
            Text(uiDto.dayOfWeek)
        }
    }
}


@Preview(widthDp = 360)
@Composable
fun PreviewActivityChart() {
    ActivityChart(items = chartItems, onItemClick = {})
}

// todo remove
val chartItems = listOf(
    ActivityItemUiDto(
        normalizedValue = 0.76f, dayOfMonth = "33", dayOfWeek = "Thu", isSelected = false
    ), ActivityItemUiDto(
        normalizedValue = 0.46f, dayOfMonth = "33", dayOfWeek = "Wed", isSelected = true
    ), ActivityItemUiDto(
        normalizedValue = 0.35f, dayOfMonth = "33", dayOfWeek = "Sun", isSelected = false
    ), ActivityItemUiDto(
        normalizedValue = 0.92f, dayOfMonth = "33", dayOfWeek = "Mon", isSelected = false
    ), ActivityItemUiDto(
        normalizedValue = 0.92f, dayOfMonth = "33", dayOfWeek = "Wed", isSelected = false
    ), ActivityItemUiDto(
        normalizedValue = 0.82f, dayOfMonth = "33", dayOfWeek = "Wed", isSelected = false
    ), ActivityItemUiDto(
        normalizedValue = 0.12f, dayOfMonth = "33", dayOfWeek = "Wed", isSelected = false
    )
)