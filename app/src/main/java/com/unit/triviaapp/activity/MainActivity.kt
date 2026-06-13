package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unit.triviaapp.R
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.network.QuizApiManager
import com.unit.triviaapp.utils.LoadingViewHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btnLoad)
        val loader = findViewById<ProgressBar>(R.id.progressLoadingPlay)

        button.setOnClickListener {
            Log.d("Trivia", "Button clicked")
            Toast.makeText(this, "Awesome questions on the way!", Toast.LENGTH_SHORT).show()
            LoadingViewHelper.showView(loader)
            button.isEnabled = false

            QuizApiManager.getQuestionsList(
                onSuccess = { questions ->
                    if(questions != null){
                        val questionsIntent = Intent(this@MainActivity, QuizActivity::class.java)
                        questionsIntent.putParcelableArrayListExtra(ConstKeys.QUESTIONS_LIST, ArrayList(questions))
                        startActivity(questionsIntent)
                    }
                    LoadingViewHelper.hideView(loader)
                    button.isEnabled = true
                },

                onError = { error ->
                    Log.e("Trivia", "Error: $error")
                }
            )
        }
    }
}