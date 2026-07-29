package com.unit.triviaapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EndlessQuestionResponse(
    val page: Int?,
    val limit: Int?,
    val total_questions: Int,
    val has_next: Boolean,
    val questions: List<Question>
): Parcelable