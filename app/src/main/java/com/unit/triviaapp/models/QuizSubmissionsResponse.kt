package com.unit.triviaapp.models

data class QuizSubmissionsResponse (
    val score: Int,
    val totalQuestions: Int,
    val accuracy: Float
)