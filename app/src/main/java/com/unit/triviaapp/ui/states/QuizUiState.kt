package com.unit.triviaapp.ui.states

import com.unit.triviaapp.enums.QuizMode
import com.unit.triviaapp.models.Question

sealed interface QuizUiState {
    data object Loading : QuizUiState

    data class Success(
        val quizMode: QuizMode,
        val questions: List<Question>
    ): QuizUiState

    data class Error(
        val error: String
    ): QuizUiState
}