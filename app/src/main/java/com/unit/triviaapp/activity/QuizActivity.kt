package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.unit.triviaapp.R
import com.unit.triviaapp.constants.ConstCardValues
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.databinding.ActivityQuizBinding
import com.unit.triviaapp.enums.QuizMode
import com.unit.triviaapp.models.Question
import com.unit.triviaapp.models.QuizConfig
import com.unit.triviaapp.models.SubmitQuizRequest
import com.unit.triviaapp.network.QuizApiManager
import com.unit.triviaapp.utils.LoadingViewHelper
import com.unit.triviaapp.utils.QuestionTimer
import kotlin.collections.set

class QuizActivity: AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private var currentQuestionIndex = 0
    private var selectedAnswers = hashMapOf<Int, Int>()
    private var questionsRemainingTime = hashMapOf<Int, Long>()
    private var lockedQuestions = mutableSetOf<Int>()
    private lateinit var button: Button
    private lateinit var backButton: Button
    private lateinit var optionsContainer: LinearLayout
    private lateinit var questionTimer: QuestionTimer
    private lateinit var questions: List<Question>
    private lateinit var quizConfig: QuizConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizConfig = intent.getSerializableExtra(ConstKeys.QUIZ_CONFIG, QuizConfig::class.java)?: return

        optionsContainer = binding.llContainer
        button = binding.btnNextQuestion
        backButton = binding.backButton

        questionTimer = QuestionTimer()

        button.isEnabled = false

        button.setOnClickListener {
            nextQuestion()
        }

        backButton.setOnClickListener {
            if(currentQuestionIndex > 0){
                questionsRemainingTime[questions[currentQuestionIndex].id] = questionTimer.getRemainingTime()
                currentQuestionIndex--
                populateQuestion()
            }
        }

        if(quizConfig.quizMode == QuizMode.DAILY){
            Log.d("Trivia", "@@@Quiz Mode Daily condition entered")
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
                    Log.d("Trivia", "@@@Daily Questions are $questions calling populate questions now")
                    populateQuestion()
                }
            },
            onError = {error ->
                Log.d("Trivia", "@@@Daily questions api call from Quiz Activity error $error")
            }
        )
    }

    private fun getEndlessQuiz(){
        Log.d("Trivia", "@@@Get Endless Quiz function entered")
        var platform_id: Int = -1
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
                    populateQuestion()
                }
            },
            onError = {error ->
                Log.d("Trivia", "@@@Endless questions api call from Quiz Activity error $error")
            }
        )
    }

    private fun nextQuestion(){
        backButton.visibility = View.VISIBLE

        button.isEnabled = false

        questionsRemainingTime[questions[currentQuestionIndex].id] = questionTimer.getRemainingTime()

        if (currentQuestionIndex == questions.size - 1) {
            questionTimer.cancelTimer()
            sendQuestions()
        }

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            populateQuestion()
        }
    }

    private fun restoreSelectedAnswer(selectedAnswer: Int, optionsContainer: LinearLayout){
        if(selectedAnswer != -1) {
            val answerCard = optionsContainer.getChildAt(selectedAnswer) as MaterialCardView
            selectCard(answerCard)
        }
        button.isEnabled = true
    }

    private fun selectCard(card: MaterialCardView){
        card.cardElevation = ConstCardValues.CARD_ELEVATION_SELECTED
        card.strokeWidth = ConstCardValues.CARD_STROKE_SELECTED
        card.setCardBackgroundColor(
            getColor(R.color.selected)
        )
    }

    private fun unselectCard(card: MaterialCardView){
        card.cardElevation = ConstCardValues.CARD_ELEVATION_DEFAULT
        card.strokeWidth = ConstCardValues.CARD_STROKE_DEFAULT
        card.setCardBackgroundColor(
            getColor(R.color.unselected)
        )
    }

    private fun populateQuestion(){
        Log.d("Trivia", "@@@Populate questions called")
            val lastQuestionIndex = currentQuestionIndex == questions.size - 1
            button.text = if(lastQuestionIndex){
                getString(R.string.submit_quiz)
            }
            else{
                getString(R.string.next_question)
            }
            if(currentQuestionIndex == 0) backButton.visibility = View.INVISIBLE
            val questionId = questions[currentQuestionIndex].id

            binding.tvQuestionCounter.text = getString(
                R.string.question_counter,
                currentQuestionIndex + 1,
                questions.size
            )

            binding.progressQuiz.progress = (currentQuestionIndex + 1) * 100 / questions.size

            binding.tvQuestion.text = questions[currentQuestionIndex].question_text

            optionsContainer.removeAllViews()

            for((index, option) in questions[currentQuestionIndex].options.withIndex()){
                populateOptionsPerQuestion(index, option, questionId)
            }

        selectedAnswers[questionId]?.let {
            restoreSelectedAnswer(it, optionsContainer)
        }

            if(lockedQuestions.contains(questionId)){
                questionTimer.cancelTimer()
                binding.tvQuestionTimer.text = 0.toString()
                Toast.makeText(this, "Time Over for this Question", Toast.LENGTH_LONG).show()
                return
            }

            else if(questionsRemainingTime[questionId] != null && !lockedQuestions.contains(questionId)){
                val remainingTimeOfQuestion = questionsRemainingTime[questionId] as Long * 1000
                resetQuestionTimer(remainingTimeOfQuestion, questionId)
            }

            else {
                resetQuestionTimer(15000, questionId)
            }
    }

    private fun populateOptionsPerQuestion(index: Int, option: String, questionId: Int){
        val answerCard = MaterialCardView(this)
        val answerText = TextView(this)
        answerText.text = option
        answerText.textSize = ConstCardValues.CARD_TEXT_SIZE
        answerCard.addView(answerText)
        answerCard.tag = index
        answerCard.setContentPadding(
            24,
            20,
            24,
            20
        )
        answerCard.radius = ConstCardValues.CARD_RADIUS
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.bottomMargin = ConstCardValues.CARD_BOTTOM_MARGIN
        answerCard.layoutParams = params
        optionsContainer.addView(answerCard)

        answerCard.setOnClickListener {

            if(lockedQuestions.contains(questionId)){
                return@setOnClickListener
            }

            for(i in 0 until optionsContainer.childCount){
                val card = optionsContainer.getChildAt(i) as MaterialCardView
                unselectCard(card)
            }

            selectCard(answerCard)
            val answerIndex = answerCard.tag
            selectedAnswers[questionId] = answerIndex as Int
            button.isEnabled = true
        }
    }

    private fun resetQuestionTimer(timerValue: Long, questionId: Int){
        questionTimer.resetTimer(timerValue,
            onTick = { runningTime ->
                binding.tvQuestionTimer.text = runningTime.toString()
            },
            onFinish = {
                if( selectedAnswers[questionId] == null ){
                    selectedAnswers[questionId] = -1
                }
                lockedQuestions.add(questionId)
                nextQuestion()
            }
        )
    }

    private fun sendQuestions(){
        Log.d("Trivia","send questions called")

        val submitQuiz = SubmitQuizRequest(
            answers = selectedAnswers
        )

        button.isEnabled = false
        LoadingViewHelper.showView(binding.progressLoadingSubmit)
        Toast.makeText(this, "Submitting your answers!", Toast.LENGTH_SHORT).show()

        QuizApiManager.submitQuiz(
            submitQuiz,
            onSuccess = { scoreResponse ->
                val resultIntent = Intent(this@QuizActivity, ResultActivity::class.java)
                resultIntent.putExtra(ConstKeys.SCORE, scoreResponse?.score)
                resultIntent.putExtra(ConstKeys.TOTAL_QUESTIONS, scoreResponse?.total_questions)
                resultIntent.putExtra(ConstKeys.ACCURACY, scoreResponse?.accuracy)
                button.isEnabled = true
                LoadingViewHelper.hideView(binding.progressLoadingSubmit)
                startActivity(resultIntent)
            },
            onError = { error ->
                Log.e("Trivia","Error: $error")
                button.isEnabled = true
                LoadingViewHelper.hideView(binding.progressLoadingSubmit)
            }
        )
    }
}