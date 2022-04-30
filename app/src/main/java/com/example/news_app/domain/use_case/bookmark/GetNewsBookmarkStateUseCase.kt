package com.example.news_app.domain.use_case.bookmark

import com.example.news_app.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsBookmarkStateUseCase @Inject constructor(private val repository: NewsRepository){
    suspend operator fun invoke(newsId:Int)=repository.getNewsBookmarkState(newsId)
}