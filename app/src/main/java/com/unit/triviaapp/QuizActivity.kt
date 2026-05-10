package com.unit.triviaapp

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class QuizActivity: AppCompatActivity() {
    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        val questions = intent.getParcelableArrayListExtra("QUESTIONS_LIST", Question::class.java)

        val textView = findViewById<TextView>(R.id.tvQuestion)
        val optionsContainer = findViewById<LinearLayout>(R.id.llOptionsContainer)

        val button = findViewById<Button>(R.id.btnNextQuestion)

        button.setOnClickListener {
            currentQuestionIndex++
            if(questions != null) populateQuestion(textView, optionsContainer, questions)
        }

        if(questions != null) populateQuestion(textView, optionsContainer, questions)

    }

    private fun populateQuestion(textView: TextView, layout: LinearLayout, questions: ArrayList<Question>){
            textView.text = questions[currentQuestionIndex].question

            layout.removeAllViews()

            for(option in questions[currentQuestionIndex].options){
                val radioButton = RadioButton(this)
                radioButton.text = option
                layout.addView(radioButton)
            }
    }
}