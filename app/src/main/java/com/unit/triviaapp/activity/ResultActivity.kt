package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unit.triviaapp.R
import com.unit.triviaapp.constants.ConstKeys
import java.util.Locale

class ResultActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        val score = findViewById<TextView>(R.id.tvScore)
        val accuracy = findViewById<TextView>(R.id.tvAccuracy)
        val performance = findViewById<TextView>(R.id.tvPerformance)
        val btnPlayAgain = findViewById<Button>(R.id.btnPlayAgain)

        val scoreFetched = intent.getIntExtra(ConstKeys.SCORE, 0)
        val totalQuestionsFetched = intent.getIntExtra(ConstKeys.TOTAL_QUESTIONS, 0)
        val accuracyFetched = intent.getFloatExtra(ConstKeys.ACCURACY, 0.0f)

        score.text = getString(
            R.string.score,
            scoreFetched,
            totalQuestionsFetched
        )

        val roundOffAccuracy = String.format(Locale.getDefault(), "%.2f%%", accuracyFetched)
        accuracy.text = getString(
            R.string.accuracy,
            roundOffAccuracy
        )

        val performanceMessage = when {
            accuracyFetched == 100f -> "Perfect Score! 🎯"
            accuracyFetched >= 90f -> "Quiz Master! 🧠"
            accuracyFetched >= 70f -> "Great Job! 🔥"
            accuracyFetched >= 50f -> "Good Attempt 👍"
            else -> "Keep Practicing 💪"
        }

        performance.text = performanceMessage

        btnPlayAgain.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }

    }
}