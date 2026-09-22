package com.unit.triviaapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.unit.triviaapp.R
import androidx.compose.ui.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Icon
import com.unit.triviaapp.ui.theme.TriviaAccent
import com.unit.triviaapp.ui.theme.TriviaBackgroundBottom
import com.unit.triviaapp.ui.theme.TriviaBackgroundMiddle
import com.unit.triviaapp.ui.theme.TriviaBackgroundTop
import com.unit.triviaapp.ui.theme.TriviaCardLavender
import com.unit.triviaapp.ui.theme.TriviaCardWarm
import com.unit.triviaapp.ui.theme.TriviaPink
import com.unit.triviaapp.ui.theme.TriviaPrimary
import com.unit.triviaapp.ui.theme.TriviaWaveLavender

private val ScreenHorizontalPadding = 24.dp
private val SectionSpacing = 32.dp
private val ElementSpacing = 12.dp

@Composable
fun HomeScreen(){
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

    ) {
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

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.trivia_icon),
                contentDescription = "Trivia Challenge logo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(
                modifier = Modifier.height(SectionSpacing)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(ElementSpacing)
            ) {
                Text(
                    text = "Trivia Challenge",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Test your knowledge against the clock",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(
                modifier = Modifier.height(SectionSpacing)
            )

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = ScreenHorizontalPadding),
                horizontalArrangement = Arrangement.spacedBy(ElementSpacing)
            ) {
                FeatureItem(
                    Icons.Default.Bolt,
                    "Timed Questions",
                    TriviaCardLavender,
                    modifier = Modifier.weight(1f)
                )

                FeatureItem(
                    Icons.Default.EmojiEvents,
                    "Challenge Yourself",
                    TriviaCardWarm,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(
                modifier = Modifier.height(SectionSpacing)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(ElementSpacing)
            ) {
                DailyQuizModeButton(
                    "Daily Quiz",
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = ScreenHorizontalPadding)
                )

                EndlessQuizButton(
                    "Endless Quiz",
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = ScreenHorizontalPadding)
                )
            }
        }
    }
}

@Composable
fun FeatureItem(
    icon: ImageVector,
    title: String,
    containerColor: Color,
    modifier: Modifier
){
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = containerColor,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = TriviaAccent
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun DailyQuizModeButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier
){
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = Color.Transparent,
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            TriviaPrimary,
                            TriviaPink,
                            TriviaAccent
                        )
                    )
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun EndlessQuizButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier
){
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(
            width = 1.5.dp,
            color = TriviaPrimary
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AllInclusive,
                contentDescription = null,
                tint = TriviaPrimary
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TriviaPrimary
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = TriviaPrimary
            )
        }
    }
}