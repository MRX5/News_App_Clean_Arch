package com.example.news_app.data.remote

import com.example.newsapp.data.remote.model.ArticlesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getNews(@Query("category")category:String,@Query("country") country:String):Response<ArticlesDto>

    @GET("Everything")
    suspend fun searchForNews(@Query("q")query:String):Response<ArticlesDto>
}