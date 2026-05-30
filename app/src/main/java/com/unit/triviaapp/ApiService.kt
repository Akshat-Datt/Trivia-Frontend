package com.unit.triviaapp

import com.unit.triviaapp.models.Question
import com.unit.triviaapp.models.QuizSubmissionsResponse
import com.unit.triviaapp.models.SubmitQuizRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("questions")
    fun getQuestions(): Call<List<Question>>

    @POST("submit/questions")
    fun submitQuestions(@Body submitQuizRequest: SubmitQuizRequest): Call<QuizSubmissionsResponse>
}