package com.example.news_app.domain.use_case.bookmark

import com.example.news_app.domain.model.News
import com.example.news_app.domain.repository.NewsRepository
import javax.inject.Inject

class AddNewsToBookmarkUseCase @Inject constructor(private val repository: NewsRepository){
    suspend operator fun invoke(news: News)=repository.addNewsToBookmark(news)
}

