package com.example.news_app.features.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.news_app.R
import com.example.news_app.databinding.DetailsFragmentBinding
import com.example.news_app.domain.model.News
import com.example.news_app.features.base.BaseFragment

class DetailsFragment : BaseFragment<DetailsFragmentBinding>(R.layout.details_fragment){

    private lateinit var news_details:News
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            val bundle=DetailsFragmentArgs.fromBundle(it)
            news_details=bundle.news
            populateUi(news_details)
        }

        setHasOptionsMenu(true)
    }

    fun populateUi(news: News){
        binding.news=news
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.action_bookmark){

            return true
        }
        return super.onOptionsItemSelected(item)
    }
}