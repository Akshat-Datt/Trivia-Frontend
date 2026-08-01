package com.unit.triviaapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question (
        val id: Int,
        val question_text: String,
        val options: List<String>,
        val platform_name: String,
        val content_type_name: String,
        val difficulty: String,
    ) : Parcelable