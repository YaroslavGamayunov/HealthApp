package com.yaroslavgamayunov.healthapp.presentation.questions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class QuestionsViewModel() : ViewModel() {
    var questions: List<QuestionData> by mutableStateOf(initialQuestionData)
        private set

    var isInTest: Boolean by mutableStateOf(false)
    private var testQuestionIndex = 0

    fun sendMessage(text: String) {
        val updatedQuestions =
            questions.toMutableList().also { it.add(QuestionData(text, isFromMe = true)) }
        if (isInTest) {
            testQuestionIndex++
            if (testQuestionIndex > mentalHealthQuestions.lastIndex) {
                updatedQuestions.add(
                    QuestionData(
                        "Тест завершен. Оценка вашего ментального здоровья - 8/10",
                        isFromMe = false
                    )
                )
                updatedQuestions.add(
                    QuestionData(
                        "Ученые говорят, что для улучшения состояния полезно ходить, советуем прогуляться :)",
                        isFromMe = false
                    )
                )
                isInTest = false
            } else {
                updatedQuestions.add(
                    QuestionData(
                        mentalHealthQuestions[testQuestionIndex],
                        isFromMe = false
                    )
                )
            }
        }
        questions = updatedQuestions
    }

    fun startTest() {
        testQuestionIndex = 0
        isInTest = true

        questions = questions.toMutableList().also {
            it.add(
                QuestionData(
                    "Начинаем тест. Вам предстоит ответить на 4 вопроса",
                    isFromMe = false
                )
            )
            it.add(
                QuestionData(
                    mentalHealthQuestions[testQuestionIndex],
                    isFromMe = false
                )
            )
        }
    }
}

private val mentalHealthQuestions = listOf(
    "#1. Контролируете ли вы свои чувства во время общения?",
    "#2. Чувствуете ли вы себя комфортно наедине с собой?",
    "#3. Вы довольны своей жизнью?",
    "#4. Вы любите людей (родители, друзья, вторая половинка) такими, какие они есть?"
)

private val initialQuestionData = listOf(
    QuestionData(
        "Здравствуйте! Я ваш ии помощник, чем могу быть полезен?",
        isFromMe = false
    ),
    QuestionData(
        "Мне надо узнать, сколько шагов в день необходимо проходить",
        isFromMe = true
    ),
    QuestionData(
        "10000 шагов - минимум", isFromMe = false
    ),
    QuestionData(
        "A cколько нужно спать каждый день?",
        isFromMe = true
    ),
    QuestionData(
        "От 6 до 8 часов, меньше и больше - не рекомендуется", isFromMe = false
    ),
    QuestionData(
        "Мне надо узнать, сколько шагов в день необходимо проходить",
        isFromMe = true
    ),
    QuestionData(
        "10000 шагов - минимум", isFromMe = false
    )
)