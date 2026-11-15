package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.enums.QuizMode
import com.unit.triviaapp.models.QuizConfig
import com.unit.triviaapp.ui.HomeScreen
import com.unit.triviaapp.ui.theme.TriviaAppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TriviaAppTheme {
                HomeScreen(
                    onDailyQuizClick = {
                        val quizConfig = QuizConfig(
                            quizMode = QuizMode.DAILY
                        )

                        val intent = Intent(this, QuizActivity::class.java)

                        intent.putExtra(
                            ConstKeys.QUIZ_CONFIG,
                            quizConfig
                        )

                        startActivity(intent)
                    },
                    onEndlessQuizClick = {
                        val intent = Intent(this, PlatformActivity::class.java)

                        startActivity(intent)
                    }
                )
            }
        }
    }

}