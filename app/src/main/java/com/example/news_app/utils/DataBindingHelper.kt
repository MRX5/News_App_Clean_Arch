package com.example.news_app.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.news_app.R
import com.squareup.picasso.Picasso

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String?) {
    Picasso.get().load(url).placeholder(R.drawable.placeholder).into(imageView)
}