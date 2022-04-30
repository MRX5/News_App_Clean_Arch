package com.example.news_app.features.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_app.domain.model.News
import com.example.news_app.domain.use_case.GetNewsUseCase
import com.example.news_app.domain.use_case.bookmark.AddNewsToBookmarkUseCase
import com.example.news_app.domain.use_case.bookmark.DeleteNewsFromBookmarkUseCase
import com.example.news_app.domain.use_case.bookmark.GetBookmarkedNewsUseCase
import com.example.news_app.domain.use_case.bookmark.GetNewsBookmarkStateUseCase
import com.example.news_app.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val addNewsToBookmarkUseCase: AddNewsToBookmarkUseCase,
    private val deleteNewsFromBookmarkUseCase: DeleteNewsFromBookmarkUseCase,
    private val getNewsBookmarkStateUseCase: GetNewsBookmarkStateUseCase
) : ViewModel() {

    private val _bookmarkState = MutableStateFlow<State<Boolean>>(State.Idle)
    val bookmarkState: StateFlow<State<Boolean>> get() = _bookmarkState

    fun addNewsToBookmark(news: News) {
        viewModelScope.launch {
            addNewsToBookmarkUseCase(news)
        }
    }

    fun deleteNewsFromBookmark(newsId: Int) {
        viewModelScope.launch {
            deleteNewsFromBookmarkUseCase.invoke(newsId)
        }
    }
    fun getNewsBookmarkState(newsId: Int) {
        viewModelScope.launch {
            getNewsBookmarkStateUseCase.invoke(newsId).onEach {
                _bookmarkState.value=it
            }.launchIn(viewModelScope)
        }
    }
}