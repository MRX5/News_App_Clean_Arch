package com.example.news_app.features.ui.details

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import com.example.news_app.R
import com.example.news_app.databinding.ActivityDetailsBinding
import com.example.news_app.domain.model.News
import com.example.news_app.features.ui.web_view.NewsWebPageActivity
import com.example.news_app.features.ui.web_view.NewsWebPageActivity.Companion.NEWS_URL
import com.example.news_app.utils.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val viewModel by viewModels<DetailsViewModel>()
    private lateinit var newsDetails: News
    private var isBookmarked=false
    private val args by navArgs<DetailsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newsDetails=args.news

        getBookmarkState()
        setupActionBar()
        populateUi(newsDetails)
    }

    private fun getBookmarkState() {
        newsDetails.id?.let { viewModel.getNewsBookmarkState(it) }
        lifecycleScope.launchWhenCreated {
            viewModel.bookmarkState.collect {
                if(it is State.Success){
                    isBookmarked=it.data
                }
            }
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.detailsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun populateUi(news: News){
        binding.news=news
        binding.goToWebPageBtn.setOnClickListener {
            val intent=Intent(this,NewsWebPageActivity::class.java).apply {
                putExtra(NEWS_URL,newsDetails.url)
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)
        if(isBookmarked){
            menu?.findItem(R.id.action_bookmark)?.setIcon(R.drawable.icon_bookmark)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.action_bookmark){
            if(isBookmarked){
                isBookmarked=false
                newsDetails.id?.let { viewModel.deleteNewsFromBookmark(it) }
                item.setIcon(R.drawable.icon_round_bookmark)
            }else{
                isBookmarked=true
                newsDetails.id?.let { viewModel.addNewsToBookmark(newsDetails) }
                item.setIcon(R.drawable.icon_bookmark)
            }
            return true
        }
        else if(item.itemId==R.id.action_share){
            shareNews()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareNews() {
        val shareIntent= Intent().apply {
            action = Intent.ACTION_SEND
            type="text/plain"
            putExtra(Intent.EXTRA_TEXT, Uri.parse(newsDetails.url))
        }
        startActivity(Intent.createChooser(shareIntent,"Share with"))

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

