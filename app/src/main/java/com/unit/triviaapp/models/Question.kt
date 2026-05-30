package com.unit.triviaapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Vector

@Parcelize
data class Question (
        val id: Int,
        val question: String,
        val options: Vector<String>
    ) : Parcelable