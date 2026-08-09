package com.unit.triviaapp.models

import com.unit.triviaapp.enums.QuizMode
import java.io.Serializable
import java.util.HashMap


data class SubmitQuizRequest (
    val quiz_mode: QuizMode,
    val platform_id: Int?,
    val answers: HashMap<Int, Int>
): Serializable