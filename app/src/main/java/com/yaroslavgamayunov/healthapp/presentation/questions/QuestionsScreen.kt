package com.yaroslavgamayunov.healthapp.presentation.questions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.yaroslavgamayunov.healthapp.R

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun QuestionsScreen(
    rootNavController: NavController,
    viewModel: QuestionsViewModel,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.questions_screen)) },
                navigationIcon = {
                    IconButton(onClick = {
                        rootNavController.navigateUp()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { contentPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            val (messages, chatBox, testStartButton) = createRefs()

            val lazyColumnListState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(viewModel.questions) {
                lazyColumnListState.animateScrollToItem(viewModel.questions.lastIndex)
            }

            LazyColumn(
                state = lazyColumnListState,
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
                viewModel.questions.forEach { question ->
                    item {
                        MessageRow(
                            question.text,
                            isFromMe = question.isFromMe
                        )
                    }
                }
            }

            ChatBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(chatBox) {
                        if (!viewModel.isInTest) {
                            bottom.linkTo(testStartButton.top)
                        } else {
                            bottom.linkTo(parent.bottom)
                        }
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                sendText = {
                    viewModel.sendMessage(it)
                }
            )

            if (!viewModel.isInTest) {
                Box(
                    Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f))
                        .constrainAs(testStartButton) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }) {
                    Button(
                        onClick = { viewModel.startTest() }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Пройти тест о ментальном здоровье")
                    }
                }
            }
        }
    }
}

data class QuestionData(
    val text: String,
    val isFromMe: Boolean,
)