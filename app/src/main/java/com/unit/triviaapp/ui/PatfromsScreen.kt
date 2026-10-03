package com.unit.triviaapp.ui

import com.unit.triviaapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unit.triviaapp.models.Platforms
import com.unit.triviaapp.ui.states.PlatformsUiState
import com.unit.triviaapp.ui.theme.TriviaAccent
import com.unit.triviaapp.ui.theme.TriviaBackgroundBottom
import com.unit.triviaapp.ui.theme.TriviaBackgroundMiddle
import com.unit.triviaapp.ui.theme.TriviaBackgroundTop
import com.unit.triviaapp.ui.theme.TriviaPink
import com.unit.triviaapp.ui.theme.TriviaPrimary
import com.unit.triviaapp.ui.theme.TriviaTextSecondary
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import com.unit.triviaapp.ui.theme.TriviaCardLavender
import com.unit.triviaapp.ui.theme.TriviaTextPrimary

private val ScreenHorizontalPadding = 24.dp
private val SectionSpacing = 32.dp
private val ElementSpacing = 12.dp

@Composable
fun PlatformsScreen(
    uiState: PlatformsUiState,
    onPlatformClick: (Platforms) -> Unit
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

    ){
        BackgroundElements()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.controller_top),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(ElementSpacing)
            ) {
                Text(
                    text = "Choose Your Platform",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                TriviaPrimary,
                                TriviaPink,
                                TriviaAccent
                            )
                        )
                    )
                )

                Text(
                    text = "Pick a platform to start your endless challenge",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 15.sp
                    ),
                    color = TriviaTextSecondary
                )
            }

            when(uiState){
                PlatformsUiState.Loading -> {
                    Text("LOADING...")
                }

                is PlatformsUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(
                            horizontal = ScreenHorizontalPadding,
                            vertical = 12.dp
                        )
                    ) {
                        items(uiState.platforms) { platform ->
                            PlatformCard(
                                platform = platform,
                                onClick = {
                                    onPlatformClick(platform)
                                }
                            )
                        }
                    }
                }

                is PlatformsUiState.Error -> {
                    Text("Error ${uiState.message}")
                }
            }
        }
    }
}

@Composable
fun PlatformCard(
    platform: Platforms,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = TriviaCardLavender
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // We'll add the icon here

            Text(
                text = platform.platform_name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge,
                color = TriviaTextPrimary
            )

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = TriviaPrimary
            )
        }
    }
}