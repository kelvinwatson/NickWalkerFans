package com.watsonlogic.nickwalkerfans.feed.viewmodel

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.watsonlogic.nickwalkerfans.R
import com.watsonlogic.nickwalkerfans.feed.model.Content
import com.watsonlogic.nickwalkerfans.feed.model.UiState
import com.watsonlogic.nickwalkerfans.feed.repository.FeedRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class FeedViewModel(
    private val resources: Resources,
    private val repository: FeedRepository
) : ViewModel() {
    private val _pageToken = MutableStateFlow<String?>(null)

    private val _content = MutableStateFlow(Content())
    private val _uiState: StateFlow<UiState> = _pageToken.flatMapLatest {
        println("uiState, calling repository with $it")
        repository.getContent(it)
    }.map { content ->
        println("uiState, mapping content with $content")
        _content.value = _content.value + content
        UiState.Ready(_content.value)
    }.catch { cause ->
        println("uiState, error with $cause")
        UiState.Error(cause.message ?: resources.getString(R.string.error_generic))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = UiState.Loading
    )

    fun setPageToken(nextPage: String? = null) {
        _pageToken.value = nextPage
    }

    val uiState: StateFlow<UiState>
        get() = _uiState

    class FeedViewModelFactory(
        private val resources: Resources,
        private val repository: FeedRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            FeedViewModel(resources, repository) as T
    }
}

private operator fun Content.plus(content: Content): Content = Content(
    posts = this.posts + content.posts,
    youTubeNextPageToken = content.youTubeNextPageToken
)
