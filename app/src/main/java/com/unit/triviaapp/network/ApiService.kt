package com.unit.triviaapp.network

import com.unit.triviaapp.models.EndlessQuestionResponse
import com.unit.triviaapp.models.Platforms
import com.unit.triviaapp.models.Question
import com.unit.triviaapp.models.QuizResultResponse
import com.unit.triviaapp.models.SubmitQuizRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("questions/daily-quiz")
    fun getQuestions(): Call<List<Question>>

    @GET("platforms")
    fun getPlatforms(): Call<List<Platforms>>

    @POST("submit/questions")
    fun submitQuestions(@Body request: SubmitQuizRequest): Call<QuizResultResponse>

    @GET("questions/endless-quiz")
    fun getEndlessQuiz(
        @Query("platform_id") platformId: Int,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): Call<EndlessQuestionResponse>
}