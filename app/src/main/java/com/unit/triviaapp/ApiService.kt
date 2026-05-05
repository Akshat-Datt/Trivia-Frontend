package com.unit.triviaapp

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("questions")
    fun getQuestions(): Call<List<Question>>
}