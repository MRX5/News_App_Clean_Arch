package com.example.news_app.utils

sealed class State<out T>{
    object Idle:State<Nothing>()
    object Loading:State<Nothing>()
    data class Success<out T>(val data:T):State<T>()
    data class Error(val type:ErrorType=ErrorType.DEFAULT, val msg:String):State<Nothing>()
}

enum class ErrorType{
    ERROR_WITHOUT_CACHE,
    ERROR_WITH_CACHE,
    DEFAULT
}