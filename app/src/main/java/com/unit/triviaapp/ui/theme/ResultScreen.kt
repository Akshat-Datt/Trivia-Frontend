package com.unit.triviaapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.unit.triviaapp.R
import com.unit.triviaapp.ui.states.ResultUiState

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
    }
}