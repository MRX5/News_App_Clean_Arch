package com.example.news_app.data.repository

import com.example.news_app.data.remote.NewsApiService
import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.utils.State
import com.example.news_app.data.local.NewsDao
import com.example.news_app.utils.NetworkHelper
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService,
    private val newsDao: NewsDao,
    private val networkHelper:NetworkHelper
): NewsRepository {

    override suspend fun getNews(category:String,country: String)= flow {

        emit(State.Loading)

        if(networkHelper.isNetworkConnected()) {
            try {
                val response = apiService.getNews(category, country)

                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        newsDao.clearNewsByCategory(category)
                        newsDao.insertNews(result.articles.map { it.toNewsEntity(category) })
                    } ?: emit(State.Error("An unknown error occurred"))
                }
            } catch (e: HttpException) {
                emit(State.Error("Oops, something went wrong!"))
            } catch (e: IOException) {
                emit(State.Error("Couldn't reach server, check your internet connection"))
            }

            val news = newsDao.getCachedNewsByCategory(category).map { it.toNews() }
            emit(State.Success(news))
        }

        else{
            emit(State.Error("Couldn't reach server, check your internet connection"))
            val cachedData=newsDao.getCachedNewsByCategory(category).map { it.toNews() }
            emit(State.Success(cachedData))

        }
    }

}