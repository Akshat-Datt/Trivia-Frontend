package com.unit.triviaapp.models

data class QuizSubmissionsResponse (
    val score: Int,
    val total_questions: Int,
    val accuracy: Float
)