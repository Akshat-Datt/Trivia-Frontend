package com.unit.triviaapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val TriviaColorTheme = lightColorScheme(
    primary = TriviaPrimary,
    background = TriviaBackground,
    onBackground = TriviaTextPrimary,
    onSurface = TriviaTextSecondary
)

@Composable
fun TriviaAppTheme(
    content: @Composable () -> Unit
){
    MaterialTheme(
        colorScheme = TriviaColorTheme,
        content = content
    )
}