package com.example.news_app.domain.di

import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.domain.use_case.GetNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetNewsUseCase(repository: NewsRepository) = GetNewsUseCase(repository)
}