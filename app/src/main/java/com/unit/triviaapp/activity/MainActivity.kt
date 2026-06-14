package com.unit.triviaapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.unit.triviaapp.constants.ConstKeys
import com.unit.triviaapp.databinding.ActivityMainBinding
import com.unit.triviaapp.network.QuizApiManager
import com.unit.triviaapp.utils.LoadingViewHelper

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
            LoadingViewHelper.showView(binding.progressLoadingPlay)
            binding.btnLoad.isEnabled = false

            QuizApiManager.getQuestionsList(
                onSuccess = { questions ->
                    if(questions != null){
                        val questionsIntent = Intent(this@MainActivity, QuizActivity::class.java)
                        questionsIntent.putParcelableArrayListExtra(ConstKeys.QUESTIONS_LIST, ArrayList(questions))
                        startActivity(questionsIntent)
                    }
                    LoadingViewHelper.hideView(binding.progressLoadingPlay)
                    binding.btnLoad.isEnabled = true
                },

                onError = { error ->
                    Log.e("Trivia", "Error: $error")
                    LoadingViewHelper.hideView(binding.progressLoadingPlay)
                    binding.btnLoad.isEnabled = true
                }
            )
        }
    }
}