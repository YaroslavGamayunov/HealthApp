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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GoalSlider(
    modifier: Modifier = Modifier,
    initialValue: Int,
    goalText: (Int) -> String,
    onSave: (Int) -> Unit,
) {
    var value by remember {
        mutableIntStateOf(initialValue)
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = value.toFloat() / 30000,
            onValueChange = { value = (it * 30000).toInt() }
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
        initialValue = 1000,
        goalText = { value ->
            "$value шагов"
        },
        onSave = {})
}