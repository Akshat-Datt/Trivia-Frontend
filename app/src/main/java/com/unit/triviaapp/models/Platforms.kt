package com.unit.triviaapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Platforms(
    val id: Int,
    val platform_name: String
): Parcelable