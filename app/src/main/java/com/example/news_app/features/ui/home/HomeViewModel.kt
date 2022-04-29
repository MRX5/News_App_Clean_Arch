package com.example.news_app.features.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_app.domain.model.News
import com.example.news_app.domain.use_case.GetNewsUseCase
import com.example.news_app.utils.Constants.BUSINESS
import com.example.news_app.utils.Constants.ENTERTAINMENT
import com.example.news_app.utils.Constants.HEALTH
import com.example.news_app.utils.Constants.SCIENCE
import com.example.news_app.utils.Constants.SPORTS
import com.example.news_app.utils.Constants.TECHNOLOGY
import com.example.news_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
): ViewModel() {

    private val _news= MutableStateFlow<State<List<News>>>(State.Idle)
    val news: StateFlow<State<List<News>>> get()=_news

    fun getNews(category:String, country:String){
        viewModelScope.launch {
            getNewsUseCase(category,country).onEach {
                _news.value = it
            }.launchIn(viewModelScope)
        }
    }

}