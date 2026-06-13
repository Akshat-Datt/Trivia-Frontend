package com.unit.triviaapp.utils

import android.os.CountDownTimer
import android.util.Log

class QuestionTimer(){
    private var defaultMaxTimer: Long = 15000

    private var countDown: CountDownTimer? = null

    fun startTimer(remainingTime: Long,
                   onTick: (Long) -> Unit,
                   onFinish: () -> Unit
    ){
        countDown = object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                onTick(millisUntilFinished/1000)
            }

            override fun onFinish() {
                onFinish()
            }
        }
        countDown?.start()
    }

    fun cancelTimer(){
        countDown?.cancel()
    }

    fun resetTimer(remainingTime: Long,
                   onTick: (Long) -> Unit,
                   onFinish: () -> Unit
    ){
        countDown?.cancel()
        Log.d("Trivia", "new default max timer $defaultMaxTimer")
        startTimer(remainingTime, onTick, onFinish)
    }
}