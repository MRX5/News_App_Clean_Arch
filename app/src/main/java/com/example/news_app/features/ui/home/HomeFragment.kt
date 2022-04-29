package com.example.news_app.features.ui.home


import android.os.Bundle
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
import com.example.news_app.utils.Constants.BUSINESS
import com.example.news_app.utils.Constants.ENTERTAINMENT
import com.example.news_app.utils.Constants.HEALTH
import com.example.news_app.utils.Constants.SCIENCE
import com.example.news_app.utils.Constants.SPORTS
import com.example.news_app.utils.Constants.TECHNOLOGY
import com.example.news_app.utils.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
const val TAG="mostafa"
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()

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
        getNews()
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

    }

    private fun setupRecyclerView() {
        binding.homeRecyclerView.apply {
            setHasFixedSize(true)
            adapter=homeAdapter
        }
    }

    private val clickListener=View.OnClickListener { view->
        when(view.id){
            R.id.chip_all_news->{
                if(viewModel.news.value !is State.Success) {
                    getNews()
                }else if(viewModel.news.value is State.Success){
                    backToChip((viewModel.news.value as State.Success<List<News>>).data)
                }
            }
            R.id.chip_business->{
                if(viewModel.business.value !is State.Success) {
                    getNews(BUSINESS)
                }else if(viewModel.business.value is State.Success){
                    backToChip((viewModel.business.value as State.Success<List<News>>).data)
                }
            }
            R.id.chip_entertainment->{
                if(viewModel.entertainment.value !is State.Success) {
                    getNews(ENTERTAINMENT)
                }else if(viewModel.entertainment.value is State.Success){
                    backToChip((viewModel.entertainment.value as State.Success<List<News>>).data)
                }
            }
            R.id.chip_health->{
                if(viewModel.health.value !is State.Success) {
                    getNews(HEALTH)
                }else if(viewModel.health.value is State.Success){
                    backToChip((viewModel.health.value as State.Success<List<News>>).data)
                }
            }
            R.id.chip_science->{
                if(viewModel.science.value !is State.Success) {
                    getNews(SCIENCE)
                }else if(viewModel.science.value is State.Success){
                    backToChip((viewModel.science.value as State.Success<List<News>>).data)
                }
            }
            R.id.chip_sports->{
                if (viewModel.sports.value !is State.Success) {
                    getNews(SPORTS)
                }else if(viewModel.sports.value is State.Success){
                    backToChip((viewModel.sports.value as State.Success<List<News>>).data)
                }
            }
            R.id.chip_technology->{
                if(viewModel.technology.value !is State.Success) {
                    getNews(TECHNOLOGY)
                }else if(viewModel.technology.value is State.Success){
                    backToChip((viewModel.technology.value as State.Success<List<News>>).data)
                }
            }
        }
    }


    private fun getNews(category:String=""){
        viewModel.getNews(category,"eg")
        viewModel.lastCheckedCategory=category
    }

    private fun observe(){
        observeCategory(viewModel.news)
        observeCategory(viewModel.sports)
        observeCategory(viewModel.health)
        observeCategory(viewModel.business)
        observeCategory(viewModel.entertainment)
        observeCategory(viewModel.science)
        observeCategory(viewModel.technology)
    }

    private fun observeCategory(categoryStateFlow: StateFlow<State<List<News>>>) {
        lifecycleScope.launchWhenCreated {
            categoryStateFlow.collect {
                when(it){
                    is State.Loading->{
                        binding.homeProgressBar.show()
                    }
                    is State.Success->{
                        clearAdapter()
                        binding.homeProgressBar.hide()
                        homeAdapter.setData(it.data)
                    }
                    is State.Error->{
                        binding.homeProgressBar.hide()
                        Toast.makeText(context,it.msg,Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }


    //clear current adapter and add current chip data
    private fun backToChip(newsList:List<News>){
        clearAdapter()
        homeAdapter.setData(newsList)
    }

    private fun clearAdapter(){
        homeAdapter.clearData()
    }

}