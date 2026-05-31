package com.unit.triviaapp.network

import com.unit.triviaapp.models.Question
import com.unit.triviaapp.models.QuizResultResponse
import com.unit.triviaapp.models.SubmitQuizRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object QuizApiManager {
    fun getQuestionsList(
        onSuccess: (List<Question>?) -> Unit,
        onError: (String) -> Unit
    ){
        RetrofitInstance.api.getQuestions().enqueue(object : Callback<List<Question>> {
            override fun onResponse(
                call: Call<List<Question>?>,
                response: Response<List<Question>?>
            ) {
                if(response.isSuccessful){
                    val questions = response.body()

                    onSuccess(questions)
                }
            }

            override fun onFailure(call: Call<List<Question>?>, t: Throwable) {
                onError(t.message.toString())
            }
        })
    }

    fun submitQuiz(
        submitQuiz: SubmitQuizRequest,
        onSuccess: (QuizResultResponse?) -> Unit,
        onError: (String) -> Unit
    ){
        RetrofitInstance.api.submitQuestions(submitQuiz).enqueue( object : Callback<QuizResultResponse> {

            override fun onResponse(
                call: Call<QuizResultResponse?>,
                response: Response<QuizResultResponse?>
            ) {
                if(response.isSuccessful){
                    val scoreResponse = response.body()

                    onSuccess(scoreResponse)
                }
            }

            override fun onFailure(call: Call<QuizResultResponse?>, t: Throwable) {
                onError(t.message.toString())
            }
        })
    }
}