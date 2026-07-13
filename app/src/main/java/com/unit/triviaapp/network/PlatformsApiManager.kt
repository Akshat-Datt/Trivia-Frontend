package com.unit.triviaapp.network

import android.util.Log
import com.unit.triviaapp.models.Platforms
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

object PlatformsApiManager {
    fun getPlatformsList(
        onSuccess: (List<Platforms>?) -> Unit,
        onError: (String) -> Unit
    ){
        try {
            RetrofitInstance.api.getPlatforms().enqueue(object : Callback<List<Platforms>> {
                override fun onResponse(
                    call: Call<List<Platforms>?>,
                    response: Response<List<Platforms>?>
                ) {
                    if(response.isSuccessful){
                        val platforms = response.body()
                        onSuccess(platforms)
                    }
                }

                override fun onFailure(call: Call<List<Platforms>?>, t: Throwable) {
                    onError(t.message.toString())
                }
            })
        }
        catch (t: Throwable){
            Log.e("Trivia", "Get Platform exception in Platforms api manager ${t.message}")
        }
    }
}