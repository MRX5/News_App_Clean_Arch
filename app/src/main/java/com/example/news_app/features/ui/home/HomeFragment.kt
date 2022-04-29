package com.example.news_app.features.ui.home


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.news_app.R
import com.example.news_app.utils.common.extentions.hide
import com.example.news_app.utils.common.extentions.show
import com.example.news_app.databinding.FragmentHomeBinding
import com.example.news_app.domain.model.News
import com.example.news_app.features.base.BaseFragment
import com.example.news_app.features.ui.home.adapter.HomeAdapter
import com.example.news_app.utils.Constants.ALL_NEWS
import com.example.news_app.utils.Constants.BUSINESS
import com.example.news_app.utils.Constants.ENTERTAINMENT
import com.example.news_app.utils.Constants.HEALTH
import com.example.news_app.utils.Constants.SCIENCE
import com.example.news_app.utils.Constants.SPORTS
import com.example.news_app.utils.Constants.TECHNOLOGY
import com.example.news_app.utils.ErrorType
import com.example.news_app.utils.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
const val TAG="mostafa"
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var currentCategory:String
    private lateinit var snackBar:Snackbar

    private val homeAdapter by lazy {
        HomeAdapter{
            val directions=HomeFragmentDirections.actionNavigationHomeToDetailsActivity(it)
            findNavController().navigate(directions)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        initializeViews()
        getNews(ALL_NEWS)
        observe()
    }

    private fun initializeViews() {
        binding.apply {
            chipAllNews.setOnClickListener(clickListener)
            chipBusiness.setOnClickListener(clickListener)
            chipEntertainment.setOnClickListener(clickListener)
            chipHealth.setOnClickListener(clickListener)
            chipScience.setOnClickListener(clickListener)
            chipSports.setOnClickListener(clickListener)
            chipTechnology.setOnClickListener(clickListener)
        }

        snackBar=Snackbar.make(binding.homeScrollView,getString(R.string.no_internet_connection),Snackbar.LENGTH_INDEFINITE)
        snackBar.animationMode=Snackbar.ANIMATION_MODE_SLIDE

    }

    private fun setupRecyclerView() {
        binding.homeRecyclerView.apply {
            setHasFixedSize(true)
            adapter=homeAdapter
        }
    }

    private val clickListener=View.OnClickListener { view->
        binding.apply {
            when (view.id) {
                R.id.chip_all_news -> {
                    if (currentCategory != ALL_NEWS) {
                        clearAdapter()
                        getNews(ALL_NEWS)
                    }
                }
                R.id.chip_business -> {
                    if (currentCategory != BUSINESS) {
                        clearAdapter()
                        getNews(BUSINESS)
                    }
                }
                R.id.chip_entertainment -> {
                    if (currentCategory != ENTERTAINMENT) {
                        clearAdapter()
                        getNews(ENTERTAINMENT)
                    }
                }
                R.id.chip_health -> {
                    if (currentCategory != HEALTH) {
                        clearAdapter()
                        getNews(HEALTH)
                    }
                }
                R.id.chip_science -> {
                    if (currentCategory != SCIENCE) {
                        clearAdapter()
                        getNews(SCIENCE)
                    }
                }
                R.id.chip_sports -> {
                    if (currentCategory != SPORTS) {
                        clearAdapter()
                        currentCategory = SPORTS
                        getNews(SPORTS)
                    }
                }
                R.id.chip_technology -> {
                    if (currentCategory != TECHNOLOGY) {
                        clearAdapter()
                        getNews(TECHNOLOGY)
                    }
                }
            }
        }
    }


    private fun getNews(category:String){
        viewModel.getNews(category,"eg")
        currentCategory=category
    }

    private fun observe(){
        lifecycleScope.launchWhenCreated {
            viewModel.news.collect {
                when(it){
                    is State.Loading->{
                        binding.homeProgressBar.show()
                        binding.errorLayout.hide()
                    }
                    is State.Success->{
                            binding.homeProgressBar.hide()
                            binding.errorLayout.hide()

                        homeAdapter.setData(it.data)
                    }
                    is State.Error->{
                        binding.homeProgressBar.hide()
                        if(it.type==ErrorType.ERROR_WITHOUT_CACHE){
                                binding.errorLayout.show()
                        }
                        else if(it.type==ErrorType.ERROR_WITH_CACHE){
                            //snackBar.show()
                            Toast.makeText(context,it.msg,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun clearAdapter(){
        homeAdapter.clearData()
    }

}