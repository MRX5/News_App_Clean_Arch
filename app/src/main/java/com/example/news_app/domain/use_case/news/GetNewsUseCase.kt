package com.example.news_app.domain.use_case.news

import android.util.Log
import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.utils.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(category:String,country:String)= newsRepository.getNews(category,country).map {
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