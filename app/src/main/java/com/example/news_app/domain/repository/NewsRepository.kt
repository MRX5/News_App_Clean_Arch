package com.example.news_app.domain.repository

import com.example.news_app.domain.model.News
import com.example.news_app.utils.State
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNews(category:String,country:String): Flow<State<List<News>>>
}