package com.example.news_app.domain.use_case.news

import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.utils.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchForNewsUseCase @Inject constructor(
    private val repository: NewsRepository,
    private val dispatcher: CoroutineDispatcher
    ){
    suspend operator fun invoke(query:String)=repository.searchForNews(query).map {
        if(it is State.Success){
            it.data.forEach {news->
                news.publishedAt=news.publishedAt?.let { date->
                    date.split("T")[0]
                }
            }
        }
        it
    }.flowOn(dispatcher)
}