package com.example.news_app.features.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_app.domain.model.News
import com.example.news_app.domain.use_case.news.SearchForNewsUseCase
import com.example.news_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchForNewsUseCase: SearchForNewsUseCase
): ViewModel() {

    private val _news=MutableStateFlow<State<List<News>>>(State.Idle)
    val news:StateFlow<State<List<News>>> get()= _news

    fun searchForNews(query:String){
        viewModelScope.launch {
            searchForNewsUseCase(query).onEach {
                _news.value=it
            }.launchIn(viewModelScope)
        }
    }
}