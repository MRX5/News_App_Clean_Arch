package com.example.news_app.domain.model.mapper

import com.example.news_app.data.local.model.BookmarkEntity
import com.example.news_app.data.local.model.NewsEntity
import com.example.news_app.domain.model.News
import com.example.news_app.domain.model.Source

object DomainMapper{
    fun toBookmarkEntity(news: News)=BookmarkEntity(
        title = news.title,
        source = Source(news.source?.name),
        description = news.description,
        publishedAt = news.publishedAt,
        urlToImage = news.urlToImage,
        id = news.id!!,
        url = news.url
    )
}