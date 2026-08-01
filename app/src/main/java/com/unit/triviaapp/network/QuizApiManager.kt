package com.unit.triviaapp.network

import android.util.Log
import com.unit.triviaapp.models.EndlessQuestionResponse
import com.unit.triviaapp.models.Question
import com.unit.triviaapp.models.QuizResultResponse
import com.unit.triviaapp.models.SubmitQuizRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object QuizApiManager {
    fun getDailyQuestionsList(
        onSuccess: (List<Question>?) -> Unit,
        onError: (String) -> Unit
    ){
        try {
            Log.d("Trivia", "@@@Quiz Api Manager getDailyQuestions entered")
            RetrofitInstance.api.getQuestions().enqueue(object : Callback<List<Question>> {
                override fun onResponse(
                    call: Call<List<Question>?>,
                    response: Response<List<Question>?>
                ) {
                    if (response.isSuccessful) {
                        val questions = response.body()
                        Log.d("Trivia", "@@@onResponse questions $questions")
                        onSuccess(questions)
                    }
                }

                override fun onFailure(call: Call<List<Question>?>, t: Throwable) {
                    Log.d("Trivia", "@@@onFailure ${t.message.toString()}")
                    onError(t.message.toString())
                }
            })
        }
        catch (t: Throwable){
            Log.e("Trivia", "GetQuestion exception in quiz api manager ${t.message}")
        }
    }

    fun getEndlessQuestionsList(
        platformId: Int,
        page:Int?,
        limit:Int?,
        onSuccess: (EndlessQuestionResponse?) -> Unit,
        onError: (String) -> Unit
    ){
        try{
            RetrofitInstance.api.getEndlessQuiz(platformId, page, limit).enqueue( object : Callback<EndlessQuestionResponse>{
                override fun onResponse(
                    call: Call<EndlessQuestionResponse?>,
                    response: Response<EndlessQuestionResponse?>
                ) {
                    if(response.isSuccessful){
                        val response = response.body()

                        onSuccess(response)
                    }
                }

                override fun onFailure(
                    call: Call<EndlessQuestionResponse?>,
                    t: Throwable
                ) {
                    onError(t.message.toString())
                }

            })
        }
        catch (t: Throwable){

        }
    }

    fun submitQuiz(
        submitQuiz: SubmitQuizRequest,
        onSuccess: (QuizResultResponse?) -> Unit,
        onError: (String) -> Unit
    ){
        try {
            RetrofitInstance.api.submitQuestions(submitQuiz)
                .enqueue(object : Callback<QuizResultResponse> {

                    override fun onResponse(
                        call: Call<QuizResultResponse?>,
                        response: Response<QuizResultResponse?>
                    ) {
                        if (response.isSuccessful) {
                            val scoreResponse = response.body()

                            onSuccess(scoreResponse)
                        }
                    }

                    override fun onFailure(call: Call<QuizResultResponse?>, t: Throwable) {
                        onError(t.message.toString())
                    }
                })
        }
        catch (t: Throwable){
            Log.e("Trivia", "Submit quiz exception in quiz api manager ${t.message}")
        }
    }
}