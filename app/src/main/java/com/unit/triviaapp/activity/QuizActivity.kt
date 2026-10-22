package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.enums.QuizMode
import com.unit.triviaapp.models.Question
import com.unit.triviaapp.models.QuizConfig
import com.unit.triviaapp.models.SubmitQuizRequest
import com.unit.triviaapp.network.QuizApiManager
import android.os.Build
import androidx.activity.compose.setContent
import com.unit.triviaapp.ui.QuizScreen
import com.unit.triviaapp.ui.states.QuizUiState
import com.unit.triviaapp.ui.theme.TriviaAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class QuizActivity: AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var platform_id = -1
    private var isDailyQuiz = false
    private var selectedAnswers = hashMapOf<Int, Int>()
    private lateinit var questions: List<Question>
    private lateinit var quizConfig: QuizConfig

    private var quizUiState by mutableStateOf<QuizUiState>(
        QuizUiState.Loading
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TriviaAppTheme {
                QuizScreen(
                    quizUiState,
                    onNextButtonClick = {
                        nextQuestion()
                    },
                    onBackButtonClick = {
                        if(currentQuestionIndex > 0){
                            currentQuestionIndex--
                        }
                    },
                    onEndlessSubmitButtonClick = {
                        sendEndlessQuizAnswersToResultActivity()
                    }
                )
            }
        }

        quizConfig = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getSerializableExtra(
                ConstKeys.QUIZ_CONFIG,
                QuizConfig::class.java
            )
        } else{
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(
                ConstKeys.QUIZ_CONFIG
            ) as? QuizConfig
        }?: return

        if(quizConfig.quizMode == QuizMode.DAILY){
            Log.d("Trivia", "@@@Quiz Mode Daily condition entered")
            isDailyQuiz = true
            getDailyQuiz()
        }
        else if(quizConfig.quizMode == QuizMode.ENDLESS){
            Log.d("Trivia", "@@@Quiz Mode Endless condition entered")
            getEndlessQuiz()
        }

    }

    private fun getDailyQuiz(){
        QuizApiManager.getDailyQuestionsList(
            onSuccess = {dailyQuestions ->
                if(dailyQuestions != null){
                    Log.d("Trivia", "@@@Daily Questions are $questions calling populate questions now")
                    questions = dailyQuestions
                    quizUiState = QuizUiState.Success(
                        quizMode = QuizMode.DAILY,
                        questions = dailyQuestions
                    )
                }
            },
            onError = {error ->
                Log.d("Trivia", "@@@Daily questions api call from Quiz Activity error $error")
                quizUiState = QuizUiState.Error(
                    error = error
                )
            }
        )
    }

    private fun getEndlessQuiz(){
        Log.d("Trivia", "@@@Get Endless Quiz function entered")
        quizConfig.platform_id?.let {
            platform_id = it
        }
        QuizApiManager.getEndlessQuestionsList(
            platform_id,
            null,
            null,
            onSuccess = {endlessQuestionsResponse ->
                Log.d("Trivia", "@@@Get Endless Quiz function on success entered and questions are ${endlessQuestionsResponse?.questions}")
                if(endlessQuestionsResponse != null){
                    questions = endlessQuestionsResponse.questions
                    quizUiState = QuizUiState.Success(
                        quizMode = QuizMode.ENDLESS,
                        questions = endlessQuestionsResponse.questions
                    )
                }
            },
            onError = {error ->
                Log.d("Trivia", "@@@Endless questions api call from Quiz Activity error $error")
                quizUiState = QuizUiState.Error(
                    error = error
                )
            }
        )
    }

    private fun nextQuestion(){
        if (currentQuestionIndex == questions.size - 1) {
            if (isDailyQuiz) {
                sendDailyQuizAnswersToResultActivity()
            }
        }

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
        }
    }

    private fun sendDailyQuizAnswersToResultActivity(){
        Log.d("Trivia","send questions called")

        val submitQuiz = SubmitQuizRequest(
            quiz_mode = QuizMode.DAILY,
            platform_id = null,
            answers = selectedAnswers
        )

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ConstKeys.ANSWERS, submitQuiz)
        startActivity(intent)
    }

    private fun sendEndlessQuizAnswersToResultActivity(){
        Log.d("Trivia","send questions called")

        val submitQuiz = SubmitQuizRequest(
            quiz_mode = QuizMode.ENDLESS,
            platform_id = platform_id,
            answers = selectedAnswers
        )

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ConstKeys.ANSWERS, submitQuiz)
        startActivity(intent)
    }
}