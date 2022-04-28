package com.example.news_app.di

import android.content.Context
import androidx.room.Room
import com.example.news_app.data.remote.NewsApiService
import com.example.news_app.utils.Constants
import com.example.news_app.data.local.NewsDao
import com.example.news_app.data.local.NewsDatabase
import com.example.news_app.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNewsApiService(client: OkHttpClient)=Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApiService::class.java)

    @Provides
    @Singleton
    fun provideInterceptor(): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val url = chain.request().url.newBuilder()
                .addQueryParameter("apiKey", "63b1f94dad044add871d1e319c630265")
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }
        return OkHttpClient().newBuilder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase =
        Room.databaseBuilder(context, NewsDatabase::class.java,Constants.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideNewsDao(database: NewsDatabase): NewsDao =database.newsDao()

    @Singleton
    @Provides
    fun provideNetworkHelper(@ApplicationContext context:Context)=NetworkHelper(context)
}