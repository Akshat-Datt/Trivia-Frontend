package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unit.triviaapp.R
import com.unit.triviaapp.models.Question
import com.unit.triviaapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btnLoad)

        button.setOnClickListener {
            Log.d("Trivia", "Button clicked")
            RetrofitInstance.api.getQuestions().enqueue(object : Callback<List<Question>> {
                override fun onResponse(
                    call: Call<List<Question>?>,
                    response: Response<List<Question>?>
                ) {
                    if(response.isSuccessful){
                        val questions = response.body()

                        println(questions)

                        if(questions != null){
                            val questionsIntent = Intent(this@MainActivity, QuizActivity::class.java)
                            questionsIntent.putParcelableArrayListExtra("QUESTIONS_LIST", ArrayList(questions))
                            startActivity(questionsIntent)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Question>?>, t: Throwable) {
                    println("Error: ${t.message}")
                }
            })
        }
    }
}