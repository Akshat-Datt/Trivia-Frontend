package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.unit.triviaapp.R
import com.unit.triviaapp.constants.ConstCardValues
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.models.Question
import com.unit.triviaapp.models.SubmitQuizRequest
import com.unit.triviaapp.network.QuizApiManager
import com.unit.triviaapp.utils.LoadingViewHelper
import com.unit.triviaapp.utils.QuestionTimer
import kotlin.collections.set

class QuizActivity: AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var remainingTime: Long = 0
    private var selectedAnswers = hashMapOf<Int, Int>()
    private var questionsRemainingTime = hashMapOf<Int, Long>()
    private var lockedQuestions = mutableSetOf<Int>()
    private lateinit var button: Button
    private lateinit var backButton: Button
    private lateinit var questionText: TextView
    private lateinit var timer: TextView
    private lateinit var questionCounter: TextView
    private lateinit var questionProgressBar: ProgressBar
    private lateinit var optionsContainer: LinearLayout
    private lateinit var questionTimer: QuestionTimer
    private lateinit var questions: ArrayList<Question>
    private lateinit var submitLoader: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        questions = intent.getParcelableArrayListExtra(ConstKeys.QUESTIONS_LIST, Question::class.java)?: return

        questionCounter = findViewById(R.id.tvQuestionCounter)
        questionProgressBar = findViewById(R.id.progressQuiz)
        questionText = findViewById(R.id.tvQuestion)
        optionsContainer = findViewById(R.id.llContainer)
        timer = findViewById(R.id.tvQuestionTimer)
        button = findViewById(R.id.btnNextQuestion)
        backButton = findViewById(R.id.backButton)
        submitLoader = findViewById(R.id.progressLoadingSubmit)

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

        populateQuestion()

    }

    private fun nextQuestion(){
        backButton.visibility = View.VISIBLE

        button.isEnabled = false

        Log.d("Trivia", "Mapping $remainingTime with questions ${questions[currentQuestionIndex].id}")
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
            val lastQuestionIndex = currentQuestionIndex == questions.size - 1
            button.text = if(lastQuestionIndex){
                getString(R.string.submit_quiz)
            }
            else{
                getString(R.string.next_question)
            }
            if(currentQuestionIndex == 0) backButton.visibility = View.INVISIBLE
            val questionId = questions[currentQuestionIndex].id

            questionCounter.text = getString(
                R.string.question_counter,
                currentQuestionIndex + 1,
                questions.size
            )

            questionProgressBar.progress = (currentQuestionIndex + 1) * 100 / questions.size

            questionText.text = questions[currentQuestionIndex].question

            optionsContainer.removeAllViews()

            for((index, option) in questions[currentQuestionIndex].options.withIndex()){
                populateOptionsPerQuestion(index, option, questionId)
            }

            if(selectedAnswers[questionId] != null){
                restoreSelectedAnswer(selectedAnswers[questionId] as Int, optionsContainer)
            }

            if(lockedQuestions.contains(questionId)){
                questionTimer.cancelTimer()
                timer.text = 0.toString()
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
                timer.text = runningTime.toString()
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
        LoadingViewHelper.showView(submitLoader)
        Toast.makeText(this, "Submitting your answers!", Toast.LENGTH_SHORT).show()

        QuizApiManager.submitQuiz(
            submitQuiz,
            onSuccess = { scoreResponse ->
                val resultIntent = Intent(this@QuizActivity, ResultActivity::class.java)
                resultIntent.putExtra(ConstKeys.SCORE, scoreResponse?.score)
                resultIntent.putExtra(ConstKeys.TOTAL_QUESTIONS, scoreResponse?.total_questions)
                resultIntent.putExtra(ConstKeys.ACCURACY, scoreResponse?.accuracy)
                button.isEnabled = true
                LoadingViewHelper.hideView(submitLoader)
                startActivity(resultIntent)
            },
            onError = { error ->
                Log.e("Trivia","Error: $error")
            }
        )
    }
}