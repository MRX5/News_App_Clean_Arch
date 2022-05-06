package com.example.news_app.features.ui.web_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.news_app.R
import com.example.news_app.databinding.ActivityNewsWebPageBinding

class NewsWebPageActivity : AppCompatActivity() {

    companion object{
        const val NEWS_URL="news-url"
    }

    private lateinit var binding:ActivityNewsWebPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNewsWebPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val newsUrl=intent.getStringExtra(NEWS_URL)?:""
        binding.webView.loadUrl(newsUrl)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.webViewToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

