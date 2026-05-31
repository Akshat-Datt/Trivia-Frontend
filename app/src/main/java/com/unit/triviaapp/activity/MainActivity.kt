package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unit.triviaapp.R
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.network.QuizApiManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btnLoad)

        button.setOnClickListener {
            Log.d("Trivia", "Button clicked")

            QuizApiManager.getQuestionsList(
                onSuccess = { questions ->
                    if(questions != null){
                        val questionsIntent = Intent(this@MainActivity, QuizActivity::class.java)
                        questionsIntent.putParcelableArrayListExtra(ConstKeys.QUESTIONS_LIST, ArrayList(questions))
                        startActivity(questionsIntent)
                    }
                },

                onError = { error ->
                    Log.e("Trivia", "Error: $error")
                }
            )
        }
    }
}