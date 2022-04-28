package com.example.newsapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class ArticlesDto(
    @SerializedName("articles")
    val articles: List<NewsDto>
)
