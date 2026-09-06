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
import androidx.compose.ui.res.painterResource
import com.unit.triviaapp.R

@Composable
fun HomeScreen(){
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
            modifier = Modifier.height(60.dp)
        )

        Text("Trivia Challenge")

        Spacer(
            modifier = Modifier.height(60.dp)
        )

        Text("Test your knowledge against the clock")

        Spacer(
            modifier = Modifier.height(60.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("⚡ Timed Questions")
            Text("🏆 Challenge Yourself")
        }
    }
}