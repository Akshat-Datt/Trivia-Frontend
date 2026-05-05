package com.unit.triviaapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btnLoad)

        button.setOnClickListener {
            RetrofitInstance.api.getQuestions().enqueue(object : retrofit2.Callback<List<Question>> {
                override fun onResponse(
                    call: Call<List<Question>?>,
                    response: Response<List<Question>?>
                ) {
                    if(response.isSuccessful){
                        val questions = response.body()
                        println(questions)
                    }
                }

                override fun onFailure(call: Call<List<Question>?>, t: Throwable) {
                    println("Error: ${t.message}")
                }
            })
        }
    }
}