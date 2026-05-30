package com.unit.triviaapp.network

import com.unit.triviaapp.models.Question
import com.unit.triviaapp.models.QuizResultResponse
import com.unit.triviaapp.models.SubmitQuizRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("questions")
    fun getQuestions(): Call<List<Question>>

    @POST("submit/questions")
    fun submitQuestions(@Body request: SubmitQuizRequest): Call<QuizResultResponse>
}