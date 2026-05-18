package com.unit.triviaapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("questions")
    fun getQuestions(): Call<List<Question>>

//    @POST("submit/questions")
//    fun submitQuestions(): Call<Int>(@Body )
}