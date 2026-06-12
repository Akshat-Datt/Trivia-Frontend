package com.unit.triviaapp.utils

import android.os.CountDownTimer

class QuestionTimer(
    onTick: (Long) -> Unit,
    onFinish: () -> Unit
){
    private var defaultMaxTimer: Long = 15000

    val countDown = object : CountDownTimer(defaultMaxTimer, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            onTick(millisUntilFinished/1000)
        }

        override fun onFinish() {
            onFinish()
        }
    }

    fun startTimer(){
        countDown.start()
    }

    fun resetTimer(){
        countDown.cancel()
        defaultMaxTimer = 15000
        startTimer()
    }
}