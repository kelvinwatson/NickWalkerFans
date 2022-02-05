package com.watsonlogic.nickwalkerfans.feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.watsonlogic.nickwalkerfans.feed.repository.FeedRepository
import com.watsonlogic.nickwalkerfans.feed.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel(private val repository: FeedRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun loadFeed() {

        viewModelScope.launch {

            repository.getPostsFeed()
        }
    }

    class FeedViewModelFactory(private val repository: FeedRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            FeedViewModel(repository) as T
    }
}
