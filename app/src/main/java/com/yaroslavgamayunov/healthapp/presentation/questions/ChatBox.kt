package com.yaroslavgamayunov.healthapp.presentation.questions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ChatBox(modifier: Modifier = Modifier, sendText: (String) -> Unit) {
    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }
    Row(modifier = modifier) {
        TextField(
            modifier = Modifier.weight(1f),
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        sendText(chatBoxValue.text)
                        chatBoxValue = TextFieldValue("")
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        contentDescription = null // todo stringResource(id = R.string.accessibility_send_message)
                    )
                }
            },
            placeholder = {
                Text(text = "Введите вопрос о здоровье")
            }
        )
    }
}


@Preview(widthDp = 360, heightDp = 500)
@Composable
private fun PreviewChatBox() {
    Column(verticalArrangement = Arrangement.Bottom) {
        ChatBox(modifier = Modifier.fillMaxWidth(), {})
    }
}