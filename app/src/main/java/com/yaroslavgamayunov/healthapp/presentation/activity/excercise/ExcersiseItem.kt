package com.yaroslavgamayunov.healthapp.presentation.activity.excercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaroslavgamayunov.healthapp.data.ExerciseSession
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun ExerciseItem(
    modifier: Modifier = Modifier,
    item: ExerciseSession,
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
            val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
            val dateLabel = dateFormatter.format(
                ZonedDateTime.ofInstant(item.startTime, ZoneId.of("Europe/Moscow"))
            )
            val startLabel = timeFormatter.format(
                ZonedDateTime.ofInstant(item.startTime, ZoneId.of("Europe/Moscow"))
            )
            val endLabel = timeFormatter.format(
                ZonedDateTime.ofInstant(item.endTime, ZoneId.of("Europe/Moscow"))
            )

            Text(
                item.title ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$dateLabel $startLabel - $endLabel",
                textAlign = TextAlign.Center
            )

            if (item.calories != null || item.distance != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (item.calories != null) {
                        Icon(Icons.Filled.LocalFireDepartment, null, tint = Color.Red)
                        Text(item.calories)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    if (item.distance != null) {
                        Icon(Icons.Filled.Route, null)
                        Text(item.distance)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewExerciseItem() {
    val time1 = Instant.now()
    val time2 = time1.minusSeconds(3600)

    ExerciseItem(
        item = ExerciseSession(
            startTime = time1,
            endTime = time2,
            id = "",
            title = "Running rueueue",
            distance = "10.4 км",
            calories = "444 ккал"
        )
    )
}