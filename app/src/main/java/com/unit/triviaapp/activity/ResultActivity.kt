package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unit.triviaapp.R
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.databinding.ActivityResultBinding
import com.unit.triviaapp.models.SubmitQuizRequest
import com.unit.triviaapp.network.QuizApiManager
import java.util.Locale

class ResultActivity: AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var submitRequest: SubmitQuizRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        submitRequest = intent.getSerializableExtra(ConstKeys.ANSWERS, SubmitQuizRequest::class.java)

        submitRequest?.let {
            QuizApiManager.submitQuiz(
                it,
                onSuccess = { quizResultResponse ->
                    val scoreFetched = quizResultResponse?.score
                    val totalQuestionsFetched = quizResultResponse?.total_questions
                    val accuracyFetched = quizResultResponse?.accuracy

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

                    accuracyFetched?.let {
                        val performanceMessage = when {
                            accuracyFetched == 100f -> "Perfect Score! 🎯"
                            accuracyFetched >= 90f -> "Quiz Master! 🧠"
                            accuracyFetched >= 70f -> "Great Job! 🔥"
                            accuracyFetched >= 50f -> "Good Attempt 👍"
                            else -> "Keep Practicing 💪"
                        }
                        binding.tvPerformance.text = performanceMessage
                    }

                    binding.btnPlayAgain.setOnClickListener {
                        startActivity(
                            Intent(this, MainActivity::class.java)
                        )
                        finish()
                    }
                },
                onError = { error ->
                    Log.d("Trivia", "Result activity error $error")
                }
            )
        }

    }
}