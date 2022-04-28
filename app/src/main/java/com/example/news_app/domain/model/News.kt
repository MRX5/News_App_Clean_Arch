package com.example.news_app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val title: String?,
    val source: Source?,
    val description: String?,
    val urlToImage: String?,
    var publishedAt: String?
): Parcelable

@Parcelize
data class Source(
    val name: String?
): Parcelable