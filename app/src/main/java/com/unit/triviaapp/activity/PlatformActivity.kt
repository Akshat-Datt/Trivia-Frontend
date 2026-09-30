package com.unit.triviaapp.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.unit.triviaapp.network.PlatformsApiManager
import com.unit.triviaapp.ui.PlatformsScreen
import com.unit.triviaapp.ui.states.PlatformUiState
import com.unit.triviaapp.ui.theme.TriviaAppTheme

class PlatformActivity: AppCompatActivity(){
    private var uiState by mutableStateOf<PlatformUiState>(
        PlatformUiState.Loading
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TriviaAppTheme {
                PlatformsScreen(
                    uiState,
                    onPlatformClick = { platform ->

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
                    uiState = PlatformUiState.Success(
                        platforms = platforms
                    )
                }
                else{
                    uiState = PlatformUiState.Error(
                        message = "Platforms are empty()"
                    )
                }
            },
            onError = {error ->
                Log.d("Trivia", "Error while fetching platforms list via platforms api manager $error")
                uiState = PlatformUiState.Error(
                    message = "Platforms returned an error"
                )
            }
        )
    }
}