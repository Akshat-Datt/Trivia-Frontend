package com.unit.triviaapp.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.unit.triviaapp.enums.QuizMode
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import com.unit.triviaapp.ui.states.QuizUiState
import com.unit.triviaapp.ui.theme.TriviaBackgroundBottom
import com.unit.triviaapp.ui.theme.TriviaBackgroundMiddle
import com.unit.triviaapp.ui.theme.TriviaBackgroundTop
import androidx.compose.ui.draw.shadow

@Composable
fun QuizScreen(
    quizUiState: QuizUiState,
    currentQuestionIndex: Int,
    selectedAnswers: Map<Int, Int>,
    onBackButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    onEndlessSubmitButtonClick: () -> Unit,
    onAnswerSelected: (Int, Int) -> Unit
){
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
        BackgroundElements()

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(0.dp, 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            when (quizUiState) {
                QuizUiState.Loading -> {
                    Text("Loading...")
                }

                is QuizUiState.Success -> {

                    QuizProgressBar(
                        currentQuestion = currentQuestionIndex + 1,
                        totalQuestions = quizUiState.questions.size,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                    )

                    QuestionCard(
                        question = quizUiState.questions[currentQuestionIndex].question_text,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    val questionId = quizUiState.questions[currentQuestionIndex].id

                    AnswerOptions(
                        options = quizUiState.questions[currentQuestionIndex].options,
                        selectedIndex = selectedAnswers[questionId],
                        onOptionSelected = { index ->
                            onAnswerSelected(questionId, index)
                        },
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if(quizUiState.quizMode == QuizMode.ENDLESS) {
                            NavigationButton(
                                buttonName = "← Back",
                                onClickAction = onBackButtonClick,
                                modifier = Modifier.weight(1f),
                                isPrimary = false
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        var isVisible = true
                        if(quizUiState.quizMode == QuizMode.ENDLESS && quizUiState.questions.size -1 == currentQuestionIndex) isVisible = false

                        var nextButtonName = "Next →"
                        if(quizUiState.quizMode == QuizMode.DAILY && quizUiState.questions.size -1 == currentQuestionIndex) nextButtonName = "SUBMIT"

                        NavigationButton(
                            buttonName = nextButtonName,
                            onClickAction = onNextButtonClick,
                            modifier = Modifier.weight(1f)
                                .visible(isVisible),
                            isPrimary = true
                        )
                    }

                    if(quizUiState.quizMode == QuizMode.ENDLESS) {
                        NavigationButton(
                            buttonName = "SUBMIT",
                            onClickAction = onEndlessSubmitButtonClick,
                            modifier = Modifier.fillMaxWidth()
                                .padding(24.dp, 12.dp),
                            isPrimary = true
                        )
                    }
                }

                is QuizUiState.Error -> {
                    Text("Error ${quizUiState.error}")
                }
            }
        }
    }
}

@Composable
fun QuestionCard(
    question: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF9F4)
        ),
        border = BorderStroke(
            width = 2.dp,
            color = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 28.dp,
                    vertical = 24.dp
                ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF35116B),
                textAlign = TextAlign.Start,
                lineHeight = 34.sp
            )
        }
    }
}

@Composable
fun AnswerOptions(
    options: List<String>,
    selectedIndex: Int?,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = selectedIndex == index

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 80.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        color = if (isSelected) {
                            Color(0xFFF0E3FF)
                        } else {
                            Color(0xFFFFF9F4)
                        }
                    )
                    .border(
                        width = if (isSelected) 2.dp else 1.5.dp,
                        color = if (isSelected) {
                            Color(0xFF873BFF)
                        } else {
                            Color(0xFFFFD8C4)
                        },
                        shape = RoundedCornerShape(24.dp)
                    )
                    .clickable {
                        onOptionSelected(index)
                    }
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Option letter badge
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(
                            color = if (isSelected) {
                                Color(0xFF7436F5)
                            } else {
                                Color(0xFFE5D5FF)
                            },
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = ('A' + index).toString(),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) {
                            Color.White
                        } else {
                            Color(0xFF5125B8)
                        }
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                // Option text
                Text(
                    text = option,
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp,
                    fontWeight = if (isSelected) {
                        FontWeight.SemiBold
                    } else {
                        FontWeight.Medium
                    },
                    color = Color(0xFF35116B)
                )

                // Selection indicator
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Selected",
                        tint = Color(0xFF7436F5),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun QuizProgressBar(
    currentQuestion: Int,
    totalQuestions: Int,
    modifier: Modifier = Modifier
) {
    val progress = if (totalQuestions > 0) {
        (currentQuestion.toFloat() / totalQuestions).coerceIn(0f, 1f)
    } else {
        0f
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Question $currentQuestion of $totalQuestions",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6336E8)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(CircleShape)
                .background(Color(0xFFE8D9FF))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF7436F5),
                                Color(0xFFE45AAB),
                                Color(0xFFFFA62B)
                            )
                        )
                    )
            )
        }
    }
}

@Composable
fun NavigationButton(
    buttonName: String,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true
) {
    Box(
        modifier = modifier
            .height(58.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(50)
            )
            .clip(RoundedCornerShape(50))
            .background(
                brush = if (isPrimary) {
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF7436F5),
                            Color(0xFFE45AAB),
                            Color(0xFFFFA62B)
                        )
                    )
                } else {
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFFF9F4),
                            Color(0xFFFFF1E8)
                        )
                    )
                }
            )
            .border(
                width = 1.5.dp,
                color = if (isPrimary) {
                    Color(0xFFFFD4F2)
                } else {
                    Color(0xFFB784E8)
                },
                shape = RoundedCornerShape(50)
            )
            .clickable(onClick = onClickAction)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (isPrimary) {
                Color.White
            } else {
                Color(0xFF5125B8)
            }
        )
    }
}