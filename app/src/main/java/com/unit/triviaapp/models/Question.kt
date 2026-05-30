package com.unit.triviaapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question (
        val id: Int,
        val question: String,
        val options: List<String>
    ) : Parcelable