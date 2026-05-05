package com.unit.triviaapp

import java.util.Vector

data class Question (
        val id: Int,
        val question: String,
        val options: Vector<String>
    )