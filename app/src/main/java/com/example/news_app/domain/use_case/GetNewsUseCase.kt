package com.example.news_app.domain.use_case

import com.example.news_app.domain.model.News
import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.utils.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(category:String,country:String): Flow<State<List<News>>> {
        return newsRepository.getNews(category,country)
    }
}