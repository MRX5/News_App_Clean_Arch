package com.example.news_app.data.repository

import android.util.Log
import com.example.news_app.data.remote.NewsApiService
import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.utils.State
import com.example.news_app.data.local.NewsDao
import com.example.news_app.data.local.model.BookmarkEntity
import com.example.news_app.domain.model.News
import com.example.news_app.domain.model.mapper.DomainMapper
import com.example.news_app.utils.ErrorType
import com.example.news_app.utils.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService,
    private val newsDao: NewsDao,
    private val networkHelper: NetworkHelper
) : NewsRepository {

    override suspend fun getNews(category: String, country: String) = flow {

        emit(State.Loading)

        if (networkHelper.isNetworkConnected()) {
            try {
                val response = apiService.getNews(category, country)

                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        newsDao.clearNewsByCategory(category)
                        newsDao.insertNews(result.articles.map { it.toNewsEntity(category) })
                    } ?: emit(
                        State.Error(
                            ErrorType.ERROR_WITHOUT_CACHE,
                            "An unknown error occurred"
                        )
                    )
                }
            } catch (e: HttpException) {
                emit(State.Error(ErrorType.ERROR_WITHOUT_CACHE, "Oops, something went wrong!"))
            } catch (e: IOException) {
                emit(State.Error(ErrorType.ERROR_WITHOUT_CACHE, "Check your internet connection"))
            }

            val news = newsDao.getCachedNewsByCategory(category).map { it.toNews() }
            emit(State.Success(news))
        } else {
            val cachedData = newsDao.getCachedNewsByCategory(category).map { it.toNews() }
            if (cachedData.isEmpty()) {
                emit(State.Error(ErrorType.ERROR_WITHOUT_CACHE, "Check your internet connection"))
            } else {
                emit(State.Error(ErrorType.ERROR_WITH_CACHE, "Check your internet connection"))
                emit(State.Success(cachedData))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getBookmarkedNews() = flow {
        emit(State.Loading)
        val news = newsDao.getBookmarkedNews().map { it.toNews() }
        if(news.isNotEmpty()) {
            emit(State.Success(news))
        }else{
            emit(State.Error(msg = "Bookmark is empty"))
        }
    }

    override suspend fun getNewsBookmarkState(newsId: Int) = flow {
        val news = newsDao.getNewsBookmarkState(newsId)
        if (news != null) emit(State.Success(true))
        else emit(State.Success(false))

    }

    override suspend fun addNewsToBookmark(news: News) {
        newsDao.addNewsToBookmark(DomainMapper.toBookmarkEntity(news))
    }

    override suspend fun deleteNewsFromBookmark(newsId: Int) {
        newsDao.deleteNewsFromBookmark(newsId)
    }

}