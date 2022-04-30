package com.example.news_app.domain.di

import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.domain.use_case.GetNewsUseCase
import com.example.news_app.domain.use_case.bookmark.AddNewsToBookmarkUseCase
import com.example.news_app.domain.use_case.bookmark.DeleteNewsFromBookmarkUseCase
import com.example.news_app.domain.use_case.bookmark.GetBookmarkedNewsUseCase
import com.example.news_app.domain.use_case.bookmark.GetNewsBookmarkStateUseCase
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

    @ViewModelScoped
    @Provides
    fun provideGetBookmarkedNewsUseCase(repository: NewsRepository) = GetBookmarkedNewsUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetNewsBookmarkStateUseCase(repository: NewsRepository) = GetNewsBookmarkStateUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideAddNewsToBookmarkUseCase(repository: NewsRepository) = AddNewsToBookmarkUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideDeleteNewsFromBookmarkUseCase(repository: NewsRepository) = DeleteNewsFromBookmarkUseCase(repository)
}