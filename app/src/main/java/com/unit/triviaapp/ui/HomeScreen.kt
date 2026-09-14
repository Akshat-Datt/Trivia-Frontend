package com.unit.triviaapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.res.painterResource
import com.unit.triviaapp.R

@Composable
fun HomeScreen(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier.height(60.dp)
            )

            Image(
                painter = painterResource(R.drawable.trivia_icon),
                contentDescription = "Trivia Challenge logo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "Trivia Challenge",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Test your knowledge against the clock",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(
                modifier = Modifier.height(60.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FeatureRow(
                    "⚡",
                    "Timed Questions",
                    modifier = Modifier.weight(1f)
                )

                FeatureRow(
                    "🏆",
                    "Challenge Yourself",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(
                modifier = Modifier.height(60.dp)
            )

            QuizModeButton(
                "Daily Quiz",
                onClick = {

                }
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            QuizModeButton(
                "Endless Quiz",
                onClick = {

                }
            )
        }
    }
}

@Composable
fun FeatureRow(
    icon: String,
    title: String,
    modifier: Modifier
){
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(
                modifier = Modifier.height(4.dp)
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
fun QuizModeButton(
    title: String,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 32.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = title
        )
    }
}