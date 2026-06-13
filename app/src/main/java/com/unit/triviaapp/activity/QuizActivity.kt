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
    private lateinit var timer: TextView
    private lateinit var questionTimer: QuestionTimer
    private lateinit var questions: ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        questions = intent.getParcelableArrayListExtra(ConstKeys.QUESTIONS_LIST, Question::class.java)?: return

        val questionCounter = findViewById<TextView>(R.id.tvQuestionCounter)
        val questionProgressBar = findViewById<ProgressBar>(R.id.progressQuiz)
        val textView = findViewById<TextView>(R.id.tvQuestion)
        val optionsContainer = findViewById<LinearLayout>(R.id.llContainer)
        timer = findViewById(R.id.tvQuestionTimer)

        questionTimer = QuestionTimer()

        button = findViewById(R.id.btnNextQuestion)
        backButton = findViewById(R.id.backButton)

        button.isEnabled = false

        button.setOnClickListener {
            nextQuestion(textView, optionsContainer, questionCounter, questionProgressBar)
        }

        backButton.setOnClickListener {
            if(currentQuestionIndex > 0){
                questionsRemainingTime[questions[currentQuestionIndex].id] = remainingTime
                currentQuestionIndex--
                populateQuestion(textView, optionsContainer, questionCounter, questionProgressBar, questions)
            }
        }

        populateQuestion(textView, optionsContainer, questionCounter, questionProgressBar, questions)

    }

    private fun nextQuestion(textView: TextView, optionsContainer: LinearLayout, questionCounter: TextView, questionProgressBar: ProgressBar){
        backButton.visibility = View.VISIBLE

        button.isEnabled = false

        Log.d("Trivia", "Mapping $remainingTime with questions ${questions[currentQuestionIndex].id}")
        questionsRemainingTime[questions[currentQuestionIndex].id] = remainingTime

        if (currentQuestionIndex == questions.size - 1) {
            questionTimer.cancelTimer()
            sendQuestions()
        }

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            populateQuestion(textView, optionsContainer, questionCounter, questionProgressBar, questions)
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

    private fun populateQuestion(textView: TextView, optionsContainer: LinearLayout, questionCounter: TextView, progressBar: ProgressBar, questions: ArrayList<Question>){
            if(currentQuestionIndex == questions.size - 1) button.text = getString(R.string.submit_quiz)
            if(currentQuestionIndex < questions.size -1) button.text = getString(R.string.next_question)
            if(currentQuestionIndex == 0) backButton.visibility = View.INVISIBLE
            val questionId = questions[currentQuestionIndex].id

            questionCounter.text = getString(
                R.string.question_counter,
                currentQuestionIndex + 1,
                questions.size
            )

            progressBar.progress = (currentQuestionIndex + 1) * 100 / questions.size

            textView.text = questions[currentQuestionIndex].question

            optionsContainer.removeAllViews()

            for((index, option) in questions[currentQuestionIndex].options.withIndex()){
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
                Log.d("Trivia", "remaining time on question ${questionId} is ${questionsRemainingTime[questionId]}")

                questionTimer.resetTimer(questionsRemainingTime[questionId] as Long * 1000,
                        onTick = { secondsRemaining ->
                    timer.text = secondsRemaining.toString()
                    remainingTime = secondsRemaining
                },
                    onFinish = {
                        if( selectedAnswers[questionId] == null ){
                            selectedAnswers[questionId] = -1
                        }
                        lockedQuestions.add(questionId)
                        nextQuestion(textView, optionsContainer, questionCounter, progressBar)
                    }
                )
            }

            else {
                questionTimer.resetTimer(15000,
                    onTick = { secondsRemaining ->
                        timer.text = secondsRemaining.toString()
                        remainingTime = secondsRemaining
                    },
                    onFinish = {
                        if( selectedAnswers[questionId] == null ){

                            selectedAnswers[questionId] = -1
                            Log.d("Trivia", "mapping selected answer ${selectedAnswers[questionId]} to question $questionId")
                        }
                        lockedQuestions.add(questionId)
                        nextQuestion(textView, optionsContainer, questionCounter, progressBar)
                    }
                )
            }
    }

    private fun sendQuestions(){
        Log.d("Trivia","send questions called")

        val submitQuiz = SubmitQuizRequest(
            answers = selectedAnswers
        )

        QuizApiManager.submitQuiz(
            submitQuiz,
            onSuccess = { scoreResponse ->
                val resultIntent = Intent(this@QuizActivity, ResultActivity::class.java)
                resultIntent.putExtra(ConstKeys.SCORE, scoreResponse?.score)
                resultIntent.putExtra(ConstKeys.TOTAL_QUESTIONS, scoreResponse?.total_questions)
                resultIntent.putExtra(ConstKeys.ACCURACY, scoreResponse?.accuracy)
                startActivity(resultIntent)
            },
            onError = { error ->
                Log.e("Trivia","Error: $error")
            }
        )
    }
}