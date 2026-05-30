package com.unit.triviaapp.models

data class QuizResultResponse (
    val score: Int,
    val total_questions: Int,
    val accuracy: Float
)