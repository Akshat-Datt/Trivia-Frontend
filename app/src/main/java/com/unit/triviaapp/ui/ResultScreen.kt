package com.unit.triviaapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.unit.triviaapp.R
import com.unit.triviaapp.ui.states.ResultUiState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import java.util.Locale
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.ui.zIndex

@Composable
fun ResultScreen(
    resultUiState: ResultUiState,
    onPlayAgainClick: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(
                id = R.drawable.quiz_result_background
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.challenge_complete_badge
                ),
                contentDescription = null,
                modifier = Modifier.size(310.dp)
                    .padding(top = 20.dp)
            )

            when(resultUiState){
                ResultUiState.Calculating -> {
                    Text("Calculating...")
                }

                is ResultUiState.Success -> {
                    ResultSuccessContent(
                        score = resultUiState.quizScoreResponse.score,
                        totalQuestions = resultUiState.quizScoreResponse.total_questions,
                        accuracy = resultUiState.quizScoreResponse.accuracy,
                        onPlayAgainClick = onPlayAgainClick
                    )
                }

                is ResultUiState.Error -> {
                    Text("Error : ${resultUiState.error}")
                }
            }
        }
    }
}

@Composable
fun ResultSuccessContent(
    score: Int,
    totalQuestions: Int,
    accuracy: Float,
    onPlayAgainClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(215.dp)
        ) {

        // Score and accuracy card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(bottom = 36.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF7EB)
            ),
            border = BorderStroke(
                width = 2.dp,
                color = Color(0xFFFFD9B5)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp, horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "YOUR SCORE",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF675B72)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = score.toString(),
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF452078)
                        )

                        Text(
                            text = " / $totalQuestions",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF675B72)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    val configuration = LocalConfiguration.current
                    val locale = remember(configuration) {
                        Locale.getDefault()
                    }

                    Text(
                        text = String.format(
                            locale,
                            "Accuracy: %.2f%%",
                            accuracy
                        ),
                        fontSize = 18.sp,
                        color = Color(0xFF675B72)
                    )
                }
        }
                Surface (
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .zIndex(1f),
                    shape = RoundedCornerShape(50),
                    color = Color(0xFFFFE9A3),
                    border = BorderStroke(
                        2.dp,
                        Color(0xFFFFD166)
                    ),
                    shadowElevation = 6.dp
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = 24.dp,
                            vertical = 10.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (
                            totalQuestions > 0 && score == totalQuestions
                        ) {
                            Text(
                                text = "🎯",
                                fontSize = 36.sp
                            )

                            Text(
                                text = "Perfect Score!",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF51321F)
                            )
                        }
                        else{
                            Text(
                                text = "💪",
                                fontSize = 36.sp
                            )

                            Text(
                                text = "Nice Work! Keep Going",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF51321F)
                            )
                        }
                    }
                }
            }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(84.dp)
                .clickable(onClick = onPlayAgainClick)
        ) {
            // Dark base visible below the main button
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 7.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFF35118B))
            )

            // Raised gradient surface
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 7.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFB64DFF),
                                Color(0xFF8138EE),
                                Color(0xFF6429D7)
                            )
                        )
                    )
                    .border(
                        width = 2.dp,
                        color = Color(0xFFD8A2FF),
                        shape = RoundedCornerShape(30.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Play Again",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}