package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.databinding.ActivityMainBinding
import com.unit.triviaapp.enums.QuizMode
import com.unit.triviaapp.models.QuizConfig

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoad.setOnClickListener {
            Log.d("Trivia", "Button clicked")
            Toast.makeText(this, "Awesome questions on the way!", Toast.LENGTH_SHORT).show()
//            LoadingViewHelper.showView(binding.progressLoadingPlay)
//            binding.btnLoad.isEnabled = false

            val quizConfig = QuizConfig(
                QuizMode.DAILY
            )

            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra(ConstKeys.QUIZ_CONFIG, quizConfig)

            startActivity(intent)
        }

        binding.btnPlatformsLoad.setOnClickListener{
            val intent = Intent(this, PlatformActivity::class.java)
            startActivity(intent)
        }
    }
}