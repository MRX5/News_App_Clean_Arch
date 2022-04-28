package com.example.news_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news_app.data.local.model.BookmarkEntity
import com.example.news_app.data.local.model.NewsEntity
import com.example.newsapp.data.utils.Converters

@Database(entities = [NewsEntity::class,BookmarkEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDatabase :RoomDatabase(){
    abstract fun newsDao(): NewsDao
}