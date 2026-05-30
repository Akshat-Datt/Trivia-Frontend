package com.unit.triviaapp.activity

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unit.triviaapp.R

class ResultActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        val score = findViewById<TextView>(R.id.tvScore)
        val totalQuestions = findViewById<TextView>(R.id.tvTotalQuestions)
        val accuracy = findViewById<TextView>(R.id.tvAccuracy)

        val scoreFetched = intent.getIntExtra("score", 0)
        val totalQuestionsFetched = intent.getIntExtra("totalQuestions", 0)
        val accuracyFetched = intent.getFloatExtra("accuracy", 0.0f)

        score.text = scoreFetched.toString()
        totalQuestions.text = totalQuestionsFetched.toString()
        accuracy.text = accuracyFetched.toString()

    }
}