package com.example.news_app.common

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

object extentions {
    fun View.show(){
        visibility= VISIBLE
    }
    fun View.hide(){
        visibility=GONE
    }
}