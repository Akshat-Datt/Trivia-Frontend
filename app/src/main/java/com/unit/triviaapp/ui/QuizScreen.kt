package com.unit.triviaapp.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.unit.triviaapp.enums.QuizMode
import com.unit.triviaapp.ui.states.QuizUiState

@Composable
fun QuizScreen(
    quizUiState: QuizUiState,
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
            if(quizUiState.quizMode == QuizMode.DAILY){
                Text("Daily Quiz")
            }
            else if(quizUiState.quizMode == QuizMode.ENDLESS){
                Text("Endless Quiz")
            }
        }

        is QuizUiState.Error -> {
            Text("Error ${quizUiState.error}")
        }
    }
}