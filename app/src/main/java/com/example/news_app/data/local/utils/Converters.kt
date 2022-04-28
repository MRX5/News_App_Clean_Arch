package com.example.newsapp.data.utils

import androidx.room.TypeConverter
import com.example.news_app.domain.model.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source) = source.name

    @TypeConverter
    fun toSource(name: String) = Source(name)
}