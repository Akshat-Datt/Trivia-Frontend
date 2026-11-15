package com.unit.triviaapp.ui.states

import com.unit.triviaapp.models.QuizResultResponse

sealed interface ResultUiState {
    data object Calculating: ResultUiState

    data class Success(
        val quizScoreResponse: QuizResultResponse
    ): ResultUiState

    data class Error(
        val error: String
    ): ResultUiState
}