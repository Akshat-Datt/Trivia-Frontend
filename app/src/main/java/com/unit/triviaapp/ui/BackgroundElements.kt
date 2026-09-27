package com.unit.triviaapp.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.unit.triviaapp.ui.theme.TriviaAccent
import com.unit.triviaapp.ui.theme.TriviaPink
import com.unit.triviaapp.ui.theme.TriviaWaveLavender

@Composable
fun BackgroundElements(){
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(220.dp)
                .offset(
                    x = (-100).dp,
                    y = (-40).dp
                )
                .background(
                    color = TriviaPink.copy(alpha = 0.08f),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.TopEnd)
                .offset(
                    x = 100.dp,
                    y = 120.dp
                )
                .background(
                    color = TriviaAccent.copy(alpha = 0.07f),
                    shape = CircleShape
                )
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .align(Alignment.BottomCenter)
        ) {
            val wave = Path().apply {

                moveTo(
                    0f,
                    size.height * 0.35f
                )

                quadraticBezierTo(
                    size.width * 0.30f,
                    size.height * 0.05f,
                    size.width * 0.60f,
                    size.height * 0.55f
                )

                quadraticBezierTo(
                    size.width * 0.82f,
                    size.height * 0.90f,
                    size.width,
                    size.height * 0.35f
                )

                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            drawPath(
                path = wave,
                color = TriviaPink.copy(alpha = 0.12f)
            )

            val secondWave = Path().apply {

                moveTo(
                    0f,
                    size.height * 0.65f
                )

                quadraticBezierTo(
                    size.width * 0.25f,
                    size.height * 0.35f,
                    size.width * 0.55f,
                    size.height * 0.75f
                )

                quadraticBezierTo(
                    size.width * 0.78f,
                    size.height,
                    size.width,
                    size.height * 0.55f
                )

                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            drawPath(
                path = secondWave,
                color = TriviaWaveLavender.copy(alpha = 0.20f)
            )
        }
    }
}