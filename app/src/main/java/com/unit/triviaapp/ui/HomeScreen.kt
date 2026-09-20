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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.ui.res.painterResource
import com.unit.triviaapp.R

private val ScreenHorizontalPadding = 24.dp
private val SectionSpacing = 32.dp
private val ElementSpacing = 12.dp

@Composable
fun HomeScreen(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
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
                    "⚡",
                    "Timed Questions",
                    modifier = Modifier.weight(1f)
                )

                FeatureItem(
                    "🏆",
                    "Challenge Yourself",
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
                QuizModeButton(
                    "Daily Quiz",
                    onClick = {

                    },
                    primary = true,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = ScreenHorizontalPadding)
                )

                QuizModeButton(
                    "Endless Quiz",
                    onClick = {

                    },
                    primary = false,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = ScreenHorizontalPadding)
                )
            }
        }
    }
}

@Composable
fun FeatureItem(
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
    onClick: () -> Unit,
    primary: Boolean = true,
    modifier: Modifier
){
    if(primary) {
        Button(
            onClick = onClick,
            modifier = modifier,
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                text = title
            )
        }
    }
    else{
        OutlinedButton(
            onClick = onClick,
            modifier = modifier,
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                text = title
            )
        }
    }
}