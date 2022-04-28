package com.example.news_app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.news_app.data.local.model.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert
    suspend fun insertNews(newsList:List<NewsEntity>)

    @Query("delete from news WHERE category= :category")
    suspend fun clearNewsByCategory(category:String)

    @Query("SELECT * FROM news WHERE category= :category")
    suspend fun getCachedNewsByCategory(category:String): List<NewsEntity>

}