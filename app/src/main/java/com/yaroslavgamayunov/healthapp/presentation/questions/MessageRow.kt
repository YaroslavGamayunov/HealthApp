package com.yaroslavgamayunov.healthapp.presentation.questions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MessageRow(
    text: String,
    isFromMe: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isFromMe) Arrangement.End else Arrangement.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.6f),
            horizontalArrangement = if (isFromMe) Arrangement.End else Arrangement.Start
        ) {
            Text(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (!isFromMe) 0.dp else 16.dp,
                            bottomEnd = if (isFromMe) 0.dp else 16.dp
                        ), color = if (isFromMe) Color.Green else Color.Cyan // todo change colors
                    )
                    .padding(all = 8.dp),
                text = text
            )
        }
    }
}


@Preview(widthDp = 360)
@Composable
private fun PreviewMessageRow() {
    LazyColumn {
        item {
            MessageRow(
                "Hello world, Hello world, Hello world, Hdkdk fnfg rgf krg rgello world, Hello world, Hello world",
                isFromMe = true
            )
        }
        item {
            MessageRow("838383838383838383838383838383", isFromMe = false)
        }
        item {
            MessageRow(
                "Hello",
                isFromMe = true
            )
        }
        item {
            MessageRow(
                "Hello",
                isFromMe = false
            )
        }
    }
}