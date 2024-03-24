package com.yaroslavgamayunov.healthapp.presentation.home.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QuestionsItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    subTitle: String,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFE0B2),
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Text(
                title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                subTitle,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Перейти")
                }
            }
        }
    }
}

@Preview(
    widthDp = 360,
    heightDp = 200
)
@Composable
private fun PreviewQuestionItem() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        QuestionsItem(
            modifier = Modifier.weight(1.0f),
            title = "ИИ-помощник",
            subTitle = "Задайте любой вопрос",
            onClick = {}
        )

        QuestionsItem(
            modifier = Modifier.weight(1.0f),
            title = "Steps",
            subTitle = "Получите полезные ответы о здоровье",
            onClick = {}
        )
    }
}