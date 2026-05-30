package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unit.triviaapp.R
import com.unit.triviaapp.models.Question
import com.unit.triviaapp.models.QuizSubmissionsResponse
import com.unit.triviaapp.models.SubmitQuizRequest
import com.unit.triviaapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizActivity: AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var questionsAnswersMap = hashMapOf<Int, Int>()

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        val questions = intent.getParcelableArrayListExtra("QUESTIONS_LIST", Question::class.java)

        val textView = findViewById<TextView>(R.id.tvQuestion)
        val optionsContainer = findViewById<RadioGroup>(R.id.rgContainer)

        button = findViewById<Button>(R.id.btnNextQuestion)

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
            button.isEnabled = false

            if(questions != null) {
                if (currentQuestionIndex == questions.size - 1) {
                    sendQuestions()
                }

                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                    populateQuestion(textView, optionsContainer, questions)
                }
            }
        }

        if(questions != null) populateQuestion(textView, optionsContainer, questions)

    }

    private fun populateQuestion(textView: TextView, radioGroup: RadioGroup, questions: ArrayList<Question>){
            if(currentQuestionIndex == questions.size - 1) button.text = getString(R.string.submit_quiz)

            textView.text = questions[currentQuestionIndex].question

            radioGroup.removeAllViews()

            for((index, option) in questions[currentQuestionIndex].options.withIndex()){
                val radioButton = RadioButton(this)
                radioButton.text = option
                radioButton.tag = index
                radioGroup.addView(radioButton)
            }
    }

    private fun sendQuestions(){
        Log.d("Trivia","send questions called")

        val submitQuiz = SubmitQuizRequest(
            answers = questionsAnswersMap
        )

        RetrofitInstance.api.submitQuestions(submitQuiz).enqueue( object : Callback<QuizSubmissionsResponse> {

            override fun onResponse(
                call: Call<QuizSubmissionsResponse?>,
                response: Response<QuizSubmissionsResponse?>
            ) {
                if(response.isSuccessful){
                    val scoreResponse = response.body()

                    val resultIntent = Intent(this@QuizActivity, ResultActivity::class.java)
                    resultIntent.putExtra("score", scoreResponse?.score)
                    resultIntent.putExtra("totalQuestions", scoreResponse?.total_questions)
                    resultIntent.putExtra("accuracy", scoreResponse?.accuracy)
                    startActivity(resultIntent)
                }
            }

            override fun onFailure(call: Call<QuizSubmissionsResponse?>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }
}