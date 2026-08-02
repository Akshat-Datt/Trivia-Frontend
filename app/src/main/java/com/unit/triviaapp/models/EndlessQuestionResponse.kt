package com.unit.triviaapp.models

data class EndlessQuestionResponse(
    val page: Int?,
    val limit: Int?,
    val total_questions: Int,
    val has_next: Boolean,
    val questions: List<Question>
)