package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.unit.triviaapp.R
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.models.Question
import com.unit.triviaapp.models.SubmitQuizRequest
import com.unit.triviaapp.network.QuizApiManager

class QuizActivity: AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var selectedAnswers = hashMapOf<Int, Int>()

    private lateinit var button: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        val questions = intent.getParcelableArrayListExtra(ConstKeys.QUESTIONS_LIST, Question::class.java)?: return

        val questionCounter = findViewById<TextView>(R.id.tvQuestionCounter)
        val questionProgressBar = findViewById<ProgressBar>(R.id.progressQuiz)
        val textView = findViewById<TextView>(R.id.tvQuestion)
        val optionsContainer = findViewById<LinearLayout>(R.id.llContainer)

        button = findViewById(R.id.btnNextQuestion)
        backButton = findViewById(R.id.backButton)

        button.isEnabled = false

        button.setOnClickListener {
            backButton.visibility = View.VISIBLE

            button.isEnabled = false

            if (currentQuestionIndex == questions.size - 1) {
                sendQuestions()
            }

            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                populateQuestion(textView, optionsContainer, questionCounter, questionProgressBar, questions)
            }

        }

        backButton.setOnClickListener {
            if(currentQuestionIndex > 0){
                currentQuestionIndex--
                populateQuestion(textView, optionsContainer, questionCounter, questionProgressBar, questions)

                val answerIndex = selectedAnswers[questions[currentQuestionIndex].id]

                restoreSelectedAnswer(answerIndex as Int, optionsContainer)
            }
        }

        populateQuestion(textView, optionsContainer, questionCounter, questionProgressBar, questions)

    }

    private fun restoreSelectedAnswer(selectedAnswer: Int, optionsContainer: LinearLayout){
        val answerCard = optionsContainer.getChildAt(selectedAnswer) as MaterialCardView
        selectCard(answerCard)
        button.isEnabled = true
    }

    private fun selectCard(card: MaterialCardView){
        card.cardElevation = 8f
        card.strokeWidth = 5
    }

    private fun unselectCards(card: MaterialCardView){
        card.cardElevation = 0f
        card.strokeWidth = 1
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
                answerText.textSize = 16f
                answerCard.addView(answerText)
                answerCard.tag = index
                answerCard.setContentPadding(
                    24,
                    20,
                    24,
                    20
                )
                answerCard.animate()
                answerCard.radius = 16f
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                params.bottomMargin = 12
                answerCard.layoutParams = params
                optionsContainer.addView(answerCard)

                answerCard.setOnClickListener {

                    for(i in 0 until optionsContainer.childCount){
                        val card = optionsContainer.getChildAt(i) as MaterialCardView
                        unselectCards(card)
                    }

                    selectCard(answerCard)
                    val answerIndex = answerCard.tag
                    selectedAnswers[questionId] = answerIndex as Int
                    button.isEnabled = true
                }
            }

            if(selectedAnswers[questions[currentQuestionIndex].id] != null){
                restoreSelectedAnswer(selectedAnswers[questions[currentQuestionIndex].id] as Int, optionsContainer)
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