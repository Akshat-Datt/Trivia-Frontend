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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.unit.triviaapp.utils.QuestionTimer

class QuizActivity: AppCompatActivity() {
    private var currentQuestionIndex by mutableIntStateOf(0)
    private var platform_id = -1
    private var isDailyQuiz = false
    private val dailyQuizTimerValue = 15000L
    private val questionTimer = QuestionTimer()
    private var selectedAnswers = mutableStateMapOf<Int, Int>()
    private lateinit var questions: List<Question>
    private lateinit var quizConfig: QuizConfig
    private var quizUiState by mutableStateOf<QuizUiState>(
        QuizUiState.Loading
    )
    private var remainingSeconds by mutableLongStateOf(15L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TriviaAppTheme {
                QuizScreen(
                    quizUiState,
                    currentQuestionIndex,
                    selectedAnswers,
                    remainingSeconds,
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
                    },
                    onAnswerSelected = { questionId, index ->
                        selectedAnswers[questionId] = index
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
                    questions = dailyQuestions
                    quizUiState = QuizUiState.Success(
                        quizMode = QuizMode.DAILY,
                        questions = dailyQuestions
                    )
                    operateTimer()
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
                questionTimer.cancelTimer()
                sendDailyQuizAnswersToResultActivity()
            }
        }

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            if(isDailyQuiz){
                operateTimer()
            }
        }
    }

    private fun sendDailyQuizAnswersToResultActivity(){
        Log.d("@@@","send questions called ${selectedAnswers}")

        val submitQuiz = SubmitQuizRequest(
            quiz_mode = QuizMode.DAILY,
            platform_id = null,
            answers = HashMap(selectedAnswers)
        )

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ConstKeys.ANSWERS, submitQuiz)
        startActivity(intent)
    }

    private fun sendEndlessQuizAnswersToResultActivity(){
        Log.d("Trivia","@@@endless send questions called with ${selectedAnswers}")

        val submitQuiz = SubmitQuizRequest(
            quiz_mode = QuizMode.ENDLESS,
            platform_id = platform_id,
            answers = HashMap(selectedAnswers)
        )

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ConstKeys.ANSWERS, submitQuiz)
        startActivity(intent)
    }

    private fun operateTimer(){
        questionTimer.startTimer(
            dailyQuizTimerValue,
            onTick = { seconds ->
                remainingSeconds = seconds
            },
            onFinish = {
                if(selectedAnswers[questions[currentQuestionIndex].id] == null) selectedAnswers[questions[currentQuestionIndex].id] = -1
                questionTimer.cancelTimer()
                nextQuestion()
            }
        )
    }
}