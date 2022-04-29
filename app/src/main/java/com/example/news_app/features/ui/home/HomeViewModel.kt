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

    var lastCheckedCategory=""

    private val _news= MutableStateFlow<State<List<News>>>(State.Idle)
    val news: StateFlow<State<List<News>>> get()=_news

    private val _sports= MutableStateFlow<State<List<News>>>(State.Idle)
    val sports: StateFlow<State<List<News>>> get()=_sports

    private val _science= MutableStateFlow<State<List<News>>>(State.Idle)
    val science: StateFlow<State<List<News>>> get()=_science

    private val _health= MutableStateFlow<State<List<News>>>(State.Idle)
    val health: StateFlow<State<List<News>>> get()=_health

    private val _technology= MutableStateFlow<State<List<News>>>(State.Idle)
    val technology: StateFlow<State<List<News>>> get()=_technology

    private val _business= MutableStateFlow<State<List<News>>>(State.Idle)
    val business: StateFlow<State<List<News>>> get()=_business

    private val _entertainment= MutableStateFlow<State<List<News>>>(State.Idle)
    val entertainment: StateFlow<State<List<News>>> get()=_entertainment

     fun getNews(category:String, country:String){
        viewModelScope.launch {
            getNewsUseCase(category,country).onEach {
                    when(category) {
                        ""->_news.value = it
                        SPORTS->{_sports.value = it}
                        SCIENCE->_science.value = it
                        HEALTH->_health.value = it
                        TECHNOLOGY->_technology.value = it
                        BUSINESS->_business.value = it
                        ENTERTAINMENT->_entertainment.value = it
                    }
            }.launchIn(viewModelScope)
        }
    }
}