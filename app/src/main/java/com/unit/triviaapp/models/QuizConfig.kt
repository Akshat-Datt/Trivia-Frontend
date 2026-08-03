package com.unit.triviaapp.models


import com.unit.triviaapp.enums.QuizMode
import java.io.Serializable


data class QuizConfig(
    val quizMode: QuizMode,
    val platform_id: Int? = null
): Serializable