package com.example.news_app.features.ui.bookmark

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.news_app.R
import com.example.news_app.databinding.FragmentBookmarkBinding
import com.example.news_app.domain.model.News
import com.example.news_app.features.base.BaseFragment
import com.example.news_app.features.ui.bookmark.adapter.BookmarkAdapter
import com.example.news_app.utils.State
import com.example.news_app.utils.common.extentions.hide
import com.example.news_app.utils.common.extentions.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(R.layout.fragment_bookmark) {
    private val viewModel by viewModels<BookmarkViewModel>()


    private val bookmarkAdapter by lazy {
        BookmarkAdapter {
            val directions = BookmarkFragmentDirections.actionNavigationBookmarkToDetailsActivity(it)
            findNavController().navigate(directions)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observe()
    }



    private fun setupRecyclerView() {
        binding.bookmarkRecyclerView.apply {
            setHasFixedSize(true)
            adapter = bookmarkAdapter
        }
    }

    private fun observe() {
        lifecycleScope.launchWhenCreated {
            viewModel.news.collect {
                when(it){
                    is State.Loading->{
                        binding.bookmarkRecyclerView.show()
                    }
                    is State.Success->{
                        binding.bookmarkRecyclerView.hide()
                        bookmarkAdapter.setData(it.data)
                    }
                    is State.Error->{
                        binding.bookmarkRecyclerView.hide()
                        binding.errorMsg.show()
                        binding.imageView.show()
                    }
                }
            }
        }
    }
}