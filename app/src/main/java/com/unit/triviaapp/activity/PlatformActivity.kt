package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.enums.QuizMode
import com.unit.triviaapp.models.QuizConfig
import com.unit.triviaapp.network.PlatformsApiManager
import com.unit.triviaapp.ui.PlatformsScreen
import com.unit.triviaapp.ui.states.PlatformsUiState
import com.unit.triviaapp.ui.theme.TriviaAppTheme

class PlatformActivity: AppCompatActivity(){
    private var uiState by mutableStateOf<PlatformsUiState>(
        PlatformsUiState.Loading
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TriviaAppTheme {
                PlatformsScreen(
                    uiState,
                    onPlatformClick = { platform ->
                        val quizConfig = QuizConfig(
                            quizMode = QuizMode.ENDLESS,
                            platform_id = platform.id
                        )

                        val intent = Intent(this, QuizActivity::class.java)
                        intent.putExtra(ConstKeys.QUIZ_CONFIG, quizConfig)

                        startActivity(intent)
                    }
                )
            }
        }

        getPlatformsList()
    }

    private fun getPlatformsList(){
        PlatformsApiManager.getPlatformsList(
            onSuccess = { platforms ->
                if(platforms != null){
                    uiState = PlatformsUiState.Success(
                        platforms = platforms
                    )
                }
                else{
                    uiState = PlatformsUiState.Error(
                        message = "No Platforms received"
                    )
                }
            },
            onError = {error ->
                Log.d("Trivia", "Error while fetching platforms list via platforms api manager $error")
                uiState = PlatformsUiState.Error(
                    message = "Platforms returned an error"
                )
            }
        )
    }
}