package com.example.news_app.domain.repository

import com.example.news_app.domain.model.News
import com.example.news_app.utils.State
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNews(category:String,country:String): Flow<State<List<News>>>
    suspend fun getBookmarkedNews():Flow<State<List<News>>>
    suspend fun getNewsBookmarkState(newsId: Int):Flow<State<Boolean>>
    suspend fun addNewsToBookmark(news: News)
    suspend fun deleteNewsFromBookmark(newsId:Int)
    suspend fun searchForNews(query:String):Flow<State<List<News>>>
}