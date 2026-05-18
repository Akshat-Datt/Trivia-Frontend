package com.unit.triviaapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class QuizActivity: AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var questionsAnswersMap = hashMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        val questions = intent.getParcelableArrayListExtra("QUESTIONS_LIST", Question::class.java)

        val textView = findViewById<TextView>(R.id.tvQuestion)
        val optionsContainer = findViewById<RadioGroup>(R.id.rgContainer)

        val button = findViewById<Button>(R.id.btnNextQuestion)

        button.isEnabled = false

        optionsContainer.setOnCheckedChangeListener { _, checkedId ->
           if(checkedId != -1 && questions != null){
               val radioButton = optionsContainer.findViewById<RadioButton>(checkedId)
               val answerIndex = radioButton.tag as Int
               val questionId = questions[currentQuestionIndex].id

               questionsAnswersMap.put(questionId, answerIndex)

               Log.d("Trivia", "Inserted in map with key $questionId and value $answerIndex")

               button.isEnabled = true
           }
        }

        button.setOnClickListener {
            currentQuestionIndex++
            button.isEnabled = false
            if(questions != null) {
                populateQuestion(textView, optionsContainer, questions)
            }
        }

        if(questions != null) populateQuestion(textView, optionsContainer, questions)

    }

    private fun populateQuestion(textView: TextView, radioGroup: RadioGroup, questions: ArrayList<Question>){
            textView.text = questions[currentQuestionIndex].question

            radioGroup.removeAllViews()

            for((index, option) in questions[currentQuestionIndex].options.withIndex()){
                val radioButton = RadioButton(this)
                radioButton.text = option
                radioButton.tag = index
                radioGroup.addView(radioButton)
            }
    }
}