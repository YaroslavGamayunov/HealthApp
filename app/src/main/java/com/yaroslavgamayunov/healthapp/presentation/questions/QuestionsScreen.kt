package com.yaroslavgamayunov.healthapp.presentation.questions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QuestionsScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (messages, chatBox) = createRefs()

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(messages) {
                    top.linkTo(parent.top)
                    bottom.linkTo(chatBox.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(all = 16.dp)
        )
        {
            item {
                MessageRow("Привет!", isFromMe = true)
            }
            item {
                MessageRow(
                    "Здравствуйте! Я ваш ии помощник, чем могу быть полезен?",
                    isFromMe = false
                )
            }
            item {
                MessageRow(
                    "Мне надо узнать, сколько шагов в день необходимо проходить",
                    isFromMe = true
                )
            }
            item {
                MessageRow("10000 шагов - минимум", isFromMe = false)
            }
            item {
                MessageRow("Привет!", isFromMe = true)
            }
            item {
                MessageRow(
                    "Здравствуйте! Я ваш ии помощник, чем могу быть полезен?",
                    isFromMe = false
                )
            }
            item {
                MessageRow(
                    "Мне надо узнать, сколько шагов в день необходимо проходить",
                    isFromMe = true
                )
            }
            item {
                MessageRow("Привет!", isFromMe = true)
            }
            item {
                MessageRow(
                    "Здравствуйте! Я ваш ии помощник, чем могу быть полезен?",
                    isFromMe = false
                )
            }
            item {
                MessageRow(
                    "Мне надо узнать, сколько шагов в день необходимо проходить",
                    isFromMe = true
                )
            }
            item {
                MessageRow("10000 шагов - минимум", isFromMe = false)
            }
            item {
                MessageRow("Привет!", isFromMe = true)
            }
            item {
                MessageRow(
                    "Здравствуйте! Я ваш ии помощник, чем могу быть полезен?",
                    isFromMe = false
                )
            }
            item {
                MessageRow(
                    "Мне надо узнать, сколько шагов в день необходимо проходить",
                    isFromMe = true
                )
            }
        }

        ChatBox(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(chatBox) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}