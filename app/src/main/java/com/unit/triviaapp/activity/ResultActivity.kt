package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unit.triviaapp.R
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.databinding.ActivityResultBinding
import java.util.Locale

class ResultActivity: AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scoreFetched = intent.getIntExtra(ConstKeys.SCORE, 0)
        val totalQuestionsFetched = intent.getIntExtra(ConstKeys.TOTAL_QUESTIONS, 0)
        val accuracyFetched = intent.getFloatExtra(ConstKeys.ACCURACY, 0.0f)

        binding.tvScore.text = getString(
            R.string.score,
            scoreFetched,
            totalQuestionsFetched
        )

        val roundOffAccuracy = String.format(Locale.getDefault(), "%.2f%%", accuracyFetched)
        binding.tvAccuracy.text = getString(
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

        binding.tvPerformance.text = performanceMessage

        binding.btnPlayAgain.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }

    }
}