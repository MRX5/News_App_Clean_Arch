package com.example.news_app.features.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.news_app.R
import com.example.news_app.databinding.FragmentSearchBinding
import com.example.news_app.features.base.BaseFragment
import com.example.news_app.features.ui.home.adapter.HomeAdapter
import com.example.news_app.features.ui.web_view.NewsWebPageActivity
import com.example.news_app.features.ui.web_view.NewsWebPageActivity.Companion.NEWS_URL
import com.example.news_app.utils.State
import com.example.news_app.utils.common.extentions.hide
import com.example.news_app.utils.common.extentions.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search),
    SearchView.OnQueryTextListener {
    private val viewModel by viewModels<SearchViewModel>()
    private val searchAdapter by lazy {
        HomeAdapter {
            val intent=Intent(context,NewsWebPageActivity::class.java).apply {
                putExtra(NEWS_URL,it.url)
            }
            startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observe()
    }

    private fun setupViews() {
        binding.searchRecyclerView.apply {
            setHasFixedSize(true)
            adapter = searchAdapter
        }
        binding.homeSearchView.setOnQueryTextListener(this)
    }

    private fun observe() {
        lifecycleScope.launchWhenCreated {
            viewModel.news.collect {
                when (it) {
                    is State.Loading -> {
                        binding.searchMessageTextView.hide()
                        binding.searchProgressBar.show()
                    }
                    is State.Success -> {
                        binding.searchProgressBar.hide()
                        searchAdapter.clearData()
                        searchAdapter.setData(it.data)
                    }
                    is State.Error -> {
                        searchAdapter.clearData()
                        binding.searchProgressBar.hide()
                        binding.searchMessageTextView.text = it.msg
                        binding.searchMessageTextView.show()
                    }
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            viewModel.searchForNews(newText)
        }

        return true
    }

}