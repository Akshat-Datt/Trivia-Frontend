package com.unit.triviaapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import java.util.Locale

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
                modifier = Modifier.size(350.dp)
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

        // Score and accuracy card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.94f)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
                        color = Color(0xFF6336E8)
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

        // Dynamic achievement message
        Text(
            text = if (
                totalQuestions > 0 && score == totalQuestions
            ) {
                "Perfect Score! 🎯"
            } else {
                "Nice Work! Keep Going 💪"
            },
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        // Play Again button
        Button(
            onClick = onPlayAgainClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFD166),
                contentColor = Color(0xFF35116B)
            )
        ) {
            Text(
                text = "Play Again",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}