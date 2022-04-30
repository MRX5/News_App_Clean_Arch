package com.example.news_app.domain.di

import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.domain.use_case.news.GetNewsUseCase
import com.example.news_app.domain.use_case.bookmark.AddNewsToBookmarkUseCase
import com.example.news_app.domain.use_case.bookmark.DeleteNewsFromBookmarkUseCase
import com.example.news_app.domain.use_case.bookmark.GetBookmarkedNewsUseCase
import com.example.news_app.domain.use_case.bookmark.GetNewsBookmarkStateUseCase
import com.example.news_app.domain.use_case.news.SearchForNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetNewsUseCase(repository: NewsRepository,dispatcher: CoroutineDispatcher) = GetNewsUseCase(repository,dispatcher)

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

    @ViewModelScoped
    @Provides
    fun provideSearchForNewsUseCase(repository: NewsRepository,dispatcher: CoroutineDispatcher):SearchForNewsUseCase = SearchForNewsUseCase(repository,dispatcher)
}