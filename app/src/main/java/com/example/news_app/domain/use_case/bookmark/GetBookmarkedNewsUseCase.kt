package com.example.news_app.domain.use_case.bookmark

import android.util.Log
import com.example.news_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBookmarkedNewsUseCase @Inject constructor(private val repository: NewsRepository){
    suspend operator fun invoke()=repository.getBookmarkedNews()
}