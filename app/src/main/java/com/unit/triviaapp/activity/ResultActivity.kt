package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.models.SubmitQuizRequest
import com.unit.triviaapp.network.QuizApiManager
import com.unit.triviaapp.ui.states.ResultUiState
import com.unit.triviaapp.ui.ResultScreen
import com.unit.triviaapp.ui.theme.TriviaAppTheme

class ResultActivity: AppCompatActivity() {
    private var resultUiState by mutableStateOf<ResultUiState>(
        ResultUiState.Calculating
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            TriviaAppTheme {
                ResultScreen(
                    resultUiState = resultUiState,
                    onPlayAgainClick = {
                        startActivity(
                            Intent(this, MainActivity::class.java)
                        )
                        finish()
                    }
                )
            }
        }

        val submitRequest = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getSerializableExtra(
                ConstKeys.ANSWERS,
                SubmitQuizRequest::class.java
            )
        } else{
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(
                ConstKeys.ANSWERS
            ) as? SubmitQuizRequest
        }

        submitRequest?.let {
            getResult(it)
        }
    }

    private fun getResult(submission: SubmitQuizRequest){
        QuizApiManager.submitQuiz(
            submission,
            onSuccess = { quizResultResponse ->
                if(quizResultResponse != null) {
                    resultUiState = ResultUiState.Success(
                        quizScoreResponse = quizResultResponse
                    )
                }
            },
            onError = { error ->
                Log.d("Trivia", "Result activity error $error")
                resultUiState = ResultUiState.Error(
                    error = error
                )
            }
        )
    }
}