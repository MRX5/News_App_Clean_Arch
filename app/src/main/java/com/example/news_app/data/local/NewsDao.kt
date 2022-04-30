package com.example.news_app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.news_app.data.local.model.BookmarkEntity
import com.example.news_app.data.local.model.NewsEntity
import com.example.news_app.domain.model.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert
    suspend fun insertNews(newsList:List<NewsEntity>)

    @Query("delete from news WHERE category= :category")
    suspend fun clearNewsByCategory(category:String)

    @Query("SELECT * FROM news WHERE category= :category")
    suspend fun getCachedNewsByCategory(category:String): List<NewsEntity>

    @Query("SELECT * FROM bookmark WHERE id=:newsId")
    suspend fun getNewsBookmarkState(newsId: Int):BookmarkEntity?

    @Insert
    suspend fun addNewsToBookmark(news:BookmarkEntity)

    @Query("DELETE from bookmark where id= :newsId")
    suspend fun deleteNewsFromBookmark(newsId:Int)

    @Query("SELECT * FROM bookmark")
    suspend fun getBookmarkedNews():List<BookmarkEntity>
}