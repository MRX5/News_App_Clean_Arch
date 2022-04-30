package com.example.news_app.domain.di

import com.example.news_app.data.remote.NewsApiService
import com.example.news_app.data.repository.NewsRepositoryImpl
import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.data.local.NewsDao
import com.example.news_app.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object RepositoriesModule {


    @ViewModelScoped
    @Provides
    fun provideNewsRepository(apiService: NewsApiService, newsDao: NewsDao,networkHelper: NetworkHelper,dispatcher: CoroutineDispatcher): NewsRepository =
        NewsRepositoryImpl(apiService,newsDao,networkHelper,dispatcher)

/*
    @ViewModelScoped
    @Provides
    fun provideAuthRepository(): AuthRepository =AuthRepositoryImpl()
*/



}