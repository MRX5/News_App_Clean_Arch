package com.example.news_app.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.news_app.domain.model.News
import com.example.news_app.domain.model.Source

@Entity(tableName = "news")
data class NewsEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val source: Source?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val url:String?,
    val category: String?,
) {
    fun toNews(): News {
        return News(
            id=id,
            title = title,
            source = source,
            description = description,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            url=url
        )
    }
}