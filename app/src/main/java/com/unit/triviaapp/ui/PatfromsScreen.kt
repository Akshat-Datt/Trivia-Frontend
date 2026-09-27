package com.unit.triviaapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.unit.triviaapp.ui.theme.TriviaBackgroundBottom
import com.unit.triviaapp.ui.theme.TriviaBackgroundMiddle
import com.unit.triviaapp.ui.theme.TriviaBackgroundTop

@Composable
fun PlatformsScreen(){
    Box (
        modifier = Modifier.fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        TriviaBackgroundTop,
                        TriviaBackgroundMiddle,
                        TriviaBackgroundBottom
                    )
                )
            )

    ){
        BackgroundElements()
    }
}