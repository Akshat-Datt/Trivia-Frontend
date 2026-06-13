package com.unit.triviaapp.utils

import android.os.CountDownTimer

class QuestionTimer(){
    private var runningTime: Long = 15000
    private var countDown: CountDownTimer? = null

    fun startTimer(remainingTime: Long,
                   onTick: (Long) -> Unit,
                   onFinish: () -> Unit
    ){
        countDown = object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                runningTime = millisUntilFinished/1000
                onTick(runningTime)
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
        startTimer(remainingTime, onTick, onFinish)
    }

    fun getRemainingTime(): Long{
        return runningTime
    }
}