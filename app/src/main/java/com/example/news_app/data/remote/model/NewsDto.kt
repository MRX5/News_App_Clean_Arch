package com.example.newsapp.data.remote.model

import com.example.news_app.data.local.model.NewsEntity
import com.example.news_app.domain.model.Source
import com.google.gson.annotations.SerializedName

data class NewsDto(
    @SerializedName("title") val title: String?,
    @SerializedName("source") val source: SourceDto?,
    @SerializedName("description") val description: String?,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("publishedAt") var publishedAt: String?,
    @SerializedName("url") val url:String?
){
    fun toNewsEntity(category:String)= NewsEntity(
        title = title,
        source = Source(source?.name),
        description = description,
        publishedAt = publishedAt,
        urlToImage = urlToImage,
        url=url,
        category = category
    )
}

data class SourceDto(
    @SerializedName("name") val name: String?
)