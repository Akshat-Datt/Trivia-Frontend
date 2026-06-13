package com.unit.triviaapp.utils

import android.view.View

object LoadingViewHelper {
    fun showView(view: View){
        view.visibility = View.VISIBLE
    }
    fun hideView(view: View){
        view.visibility = View.GONE
    }
}