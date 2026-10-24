package com.unit.triviaapp.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.unit.triviaapp.enums.QuizMode
import com.unit.triviaapp.ui.states.QuizUiState

@Composable
fun QuizScreen(
    quizUiState: QuizUiState,
    currentQuestionIndex: Int,
    onBackButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    onEndlessSubmitButtonClick: () -> Unit
){
    BackgroundElements()

    when(quizUiState){
        QuizUiState.Loading -> {
            Text("Loading...")
        }

        is QuizUiState.Success -> {
            Text("${quizUiState.questions[currentQuestionIndex].question_text}")

            Button(
                onClick = onNextButtonClick
            ) {
                Text("Next")
            }
        }

        is QuizUiState.Error -> {
            Text("Error ${quizUiState.error}")
        }
    }
}