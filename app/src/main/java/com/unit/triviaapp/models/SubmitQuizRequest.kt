package com.unit.triviaapp.models

import java.io.Serializable
import java.util.HashMap


data class SubmitQuizRequest (
    val answers: HashMap<Int, Int>
): Serializable