package com.unit.triviaapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Date

@Parcelize
data class Question (
        val id: Int,
        val question_text: String,
        val options: List<String>,
        val platform_id: Int,
        val content_type_id: Int,
        val difficulty: String,
        val challenge_date: String?
    ) : Parcelable