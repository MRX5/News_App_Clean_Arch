package com.example.news_app.data.repository

import android.util.Log
import com.example.news_app.data.remote.NewsApiService
import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.utils.State
import com.example.news_app.data.local.NewsDao
import com.example.news_app.utils.ErrorType
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
                    } ?: emit(State.Error(ErrorType.ERROR_WITHOUT_CACHE,"An unknown error occurred"))
                }
            } catch (e: HttpException) {
                emit(State.Error(ErrorType.ERROR_WITHOUT_CACHE,"Oops, something went wrong!"))
            } catch (e: IOException) {
                emit(State.Error(ErrorType.ERROR_WITHOUT_CACHE,"Check your internet connection"))
            }

            val news = newsDao.getCachedNewsByCategory(category).map { it.toNews() }
            emit(State.Success(news))
        }

        else{
            val cachedData=newsDao.getCachedNewsByCategory(category).map { it.toNews() }
            if(cachedData.isEmpty()){
                emit(State.Error(ErrorType.ERROR_WITHOUT_CACHE,"Check your internet connection"))
            }else {
                emit(State.Error(ErrorType.ERROR_WITH_CACHE,"Check your internet connection"))
                emit(State.Success(cachedData))
            }
        }
    }

}