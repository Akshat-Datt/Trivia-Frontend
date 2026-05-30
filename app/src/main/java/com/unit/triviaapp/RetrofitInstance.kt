package com.unit.triviaapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val HOME_BASE_URL = "http://192.168.1.3:9090/"
    private const val OFFICE_BASE_URL = "http://172.20.10.4:9090/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(HOME_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}