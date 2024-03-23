package com.yaroslavgamayunov.healthapp.presentation.goals

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GoalSlider(
    modifier: Modifier = Modifier,
    initialValue: Float,
    maxValue: Float,
    goalText: (Float) -> String,
    onSave: (Float) -> Unit,
) {
    var value by remember {
        mutableFloatStateOf(initialValue)
    }

    Card(modifier = modifier) {
        Text(
            goalText(value), // todo
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        Slider(
            valueRange = 0.001f..1f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = value / maxValue,
            onValueChange = { value = (it * maxValue) }
        )
        if (value != initialValue) {
            Button(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth(),
                onClick = { onSave(value) },
            ) {
                Text("Cохранить цель")
            }
        }
    }
}

@Preview(widthDp = 360)
@Composable
fun PreviewStepsGoal() {
    GoalSlider(
        modifier = Modifier.padding(16.dp),
        initialValue = 1000f,
        goalText = { value ->
            "${value.toInt()} шагов"
        },
        maxValue = 10000f,
        onSave = {})
}